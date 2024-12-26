package fci.swe.advanced_software.services;

import fci.swe.advanced_software.dtos.NotificationDto;
import fci.swe.advanced_software.dtos.NotificationRequestDto;
import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.models.Notification;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.NotificationRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.NotificationMapper;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationsService implements INotificationsService {
    private final NotificationRepository notificationRepository;
    private final AuthUtils authUtils;
    private final RepositoryUtils repositoryUtils;
    private final NotificationMapper notificationMapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AbstractUserRepository<AbstractUser> abstractUserRepository;
    private final int THREAD_POOL_SIZE = Math.min(5, Runtime.getRuntime().availableProcessors());
    private final int BATCH_SIZE = 200;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    @Override
    public ResponseEntity<Response> getNotifications(String filter, Integer page, Integer size) {
        String userId = authUtils.getCurrentUserId();
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Notification> notificationPage;
        if (filter.equals("all")) {
            notificationPage = notificationRepository.findByRecipientId(userId, pageable);
        } else if (filter.equals("unread")) {
            notificationPage = notificationRepository.findByRecipientIdAndIsReadFalse(userId, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter value");
        }
        List<NotificationDto> notifications = notificationPage.map(notificationMapper::toDto).toList();
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Notifications retrieved successfully")
                .withData("notifications", notifications)
                .build();
    }


    @Override
    @Async("taskExecutor")
    public void broadcastNotification(String title, String message, String courseId, Role role) {
        log.info("Broadcasting notification to all users with role: {} using {} threads", role, THREAD_POOL_SIZE);

        if (role == Role.ADMIN) {
            throw new RuntimeException("Invalid role");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (role == Role.INSTRUCTOR) {
            sendNotification(title, message, courseId, course.getInstructor().getId());
            return;
        }

        long totalEnrollments = enrollmentRepository.countByCourseId(courseId);
        int totalPages = (int) Math.ceil((double) totalEnrollments / BATCH_SIZE);

//        AtomicInteger processedPages = new AtomicInteger(0);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        Instant startTime = Instant.now();

        for (int pageNumber = 0; pageNumber < totalPages; pageNumber++) {
            final int currentPage = pageNumber;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    processPage(currentPage, title, message, course);
                    log.info("Processed page {}/{}", currentPage, totalPages);
                } catch (Exception e) {
                    log.error("Error processing page {}: {}", currentPage, e.getMessage(), e);
                    throw new RuntimeException("Failed to process page " + currentPage, e);
                }
            }, executorService);

            futures.add(future);
        }


        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenComplete((_, throwable) -> {
                    if (throwable != null) {
                        log.error("Error during notification broadcast: {}", throwable.getMessage(), throwable);
                    } else {
                        log.info("Successfully completed broadcasting notifications to {} pages", totalPages);
                    }
                    logExecutionTime(startTime);
                });
    }

    private void processPage(int pageNumber, String title, String message, Course course) {
        Page<Enrollment> enrollmentPage = enrollmentRepository.findAllByCourseId(
                course.getId(),
                PageRequest.of(pageNumber, BATCH_SIZE)
        );

        List<Notification> notifications = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentPage.getContent();

        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment enrollment = enrollments.get(i);
            Notification notification = Notification.builder()
                    .title(title)
                    .content(message)
                    .isRead(false)
                    .recipient(enrollment.getStudent())
                    .course(course)
                    .build();
            notifications.add(notification);
        }

        notificationRepository.saveAll(notifications);
        log.info("Saved {} notifications for page {}", notifications.size(), pageNumber);
    }

    @Override
    public void sendNotification(String title, String message, String courseId, String userId) {
        log.trace("Sending notification to user with id: {}", userId);
        Notification notification = Notification.builder()
                .title(title)
                .content(message)
                .isRead(false)
                .recipient(abstractUserRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .course(courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found")))
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public ResponseEntity<Response> markAsRead(List<NotificationRequestDto> notifications) {
        String userId = authUtils.getCurrentUserId();
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notificationRepository.findById(notifications.get(i).getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));
            if (!notification.getRecipient().getId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to mark this notification as read");
            }
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
        notificationRepository.flush();
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Notifications marked as read successfully")
                .build();
    }

    private void logExecutionTime(Instant startTime) {
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        log.info("Total execution time: {} seconds ({} ms) ({} threads)",
                duration.getSeconds(),
                duration.toMillis(),
                THREAD_POOL_SIZE);
    }

    @PreDestroy
    public void cleanup() {
        executorService.shutdown();
    }
}
