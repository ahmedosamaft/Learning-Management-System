package fci.swe.advanced_software.services;

import fci.swe.advanced_software.dtos.NotificationDto;
import fci.swe.advanced_software.dtos.NotificationRequestDto;
import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.models.Notification;
import fci.swe.advanced_software.models.courses.Course;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public void broadcastNotification(String title, String message, String courseId, Role role) {
        if (role == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
        }
        List<String> userIds;
        if (role == Role.INSTRUCTOR) {
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
            userIds = List.of(course.getInstructor().getId());
        } else {
            userIds = enrollmentRepository
                    .findAllByCourseId(courseId)
                    .stream()
                    .map(enrollment -> enrollment.getStudent().getId())
                    .toList();
        }

        userIds.forEach(userId -> CompletableFuture.runAsync(() -> sendNotification(title, message, courseId, userId)));
    }

    @Override
    public void sendNotification(String title, String message, String courseId, String userId) {
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
}
