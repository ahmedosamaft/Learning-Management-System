package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.lesson.ILessonService;
import fci.swe.advanced_software.services.users.student.IStudentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> getLessons(@PathVariable String courseId,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return lessonService.getAllLessons(courseId, page, size);
    }

    @GetMapping("/{lesson_id}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsLesson(#courseId, #lesson_id)
            """)
    public ResponseEntity<?> getLesson(@PathVariable String courseId, @PathVariable String lesson_id) {
        return lessonService.getLessonById(lesson_id);
    }

    @PostMapping("/{lesson_id}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsLesson(#courseId, #lesson_id)
            """)
    public ResponseEntity<?> attendLesson(@PathVariable String courseId,
                                          @PathVariable String lesson_id,
                                          @RequestParam(name = "otp", defaultValue = "") String otp) {
        return studentService.attendLesson(lesson_id, otp);
    }
}
