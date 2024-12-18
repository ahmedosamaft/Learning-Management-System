package fci.swe.advanced_software.services.courses.lesson;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.LessonMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public ResponseEntity<?> getAllLessons(Pageable pageable) {
        Page<Lesson> lessonsPage = lessonRepository.findAll(pageable);
        Page<LessonDto> lessonsDto = lessonsPage.map(lessonMapper::toDto);
        return buildSuccessResponse("Lessons retrieved successfully", lessonsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLessonById(String id) {
        Optional<Lesson> lessonOpt = lessonRepository.findById(id);
        return lessonOpt
                .map(lesson -> ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withMessage("Lesson retrieved successfully")
                        .withData(lessonMapper.toDto(lesson))
                        .build())
                .orElseGet(() -> ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.NOT_FOUND)
                        .withMessage("Lesson not found")
                        .build());
    }

    @Override
    public ResponseEntity<?> createLesson(LessonDto lessonDto) {
        Lesson lesson = lessonMapper.toEntity(lessonDto);

        // Auto-generate the lesson OTP
        lesson.setOtp(generateOtp());

        Lesson savedLesson = lessonRepository.save(lesson);
        String location = Constants.API_VERSION + "/lessons/" + savedLesson.getId();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Lesson created successfully")
                .withData(lessonMapper.toDto(savedLesson))
                .withLocation(location)
                .build();
    }

    @Override
    public ResponseEntity<?> updateLesson(String id, LessonDto lessonDto) {
        Optional<Lesson> lessonOpt = lessonRepository.findById(id);
        if (lessonOpt.isEmpty()) {
            return createErrorResponse("Lesson not found", HttpStatus.NOT_FOUND);
        }

        Lesson existingLesson = lessonOpt.get();
        lessonMapper.updateEntityFromDto(lessonDto, existingLesson);

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return buildSuccessResponse("Lesson updated successfully", lessonMapper.toDto(updatedLesson), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteLesson(String id) {
        if (!lessonRepository.existsById(id)) {
            return createErrorResponse("Lesson not found", HttpStatus.NOT_FOUND);
        }

        lessonRepository.deleteById(id);
        return buildSuccessResponse("Lesson deleted successfully", null, HttpStatus.NO_CONTENT);
    }

    // Helper method for generating OTP
    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 9000) + 1000); // Generates a 4-digit OTP
    }

    // Helper method for success responses
    private ResponseEntity<?> buildSuccessResponse(String message, Object data, HttpStatus status) {
        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .withData(data)
                .build();
    }

    // Helper method for error responses
    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .build();
    }
}
