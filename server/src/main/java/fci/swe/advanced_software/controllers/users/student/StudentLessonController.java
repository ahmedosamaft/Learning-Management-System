package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.lesson.ILessonService;
import fci.swe.advanced_software.services.users.student.IStudentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.STUDENT_CONTROLLER + "/lessons")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentLessonController {
    private final ILessonService lessonService;
    private final IStudentService studentService;

    @GetMapping
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getLessons(@PathVariable @UUID String courseId,
                                        @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                        @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return lessonService.getAllLessons(courseId, page, size);
    }

    @GetMapping("/{lessonId}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsLesson(#courseId, #lessonId)
            """)
    public ResponseEntity<?> getLesson(@PathVariable @UUID String courseId, @PathVariable @UUID String lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @PostMapping("/{lessonId}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsLesson(#courseId, #lessonId)
            """)
    public ResponseEntity<?> attendLesson(@PathVariable @UUID String courseId,
                                          @PathVariable @UUID String lessonId,
                                          @RequestParam(name = "otp", defaultValue = "") @Length(min = 4, max = 4) String otp) {
        return studentService.attendLesson(lessonId, otp);
    }
}
