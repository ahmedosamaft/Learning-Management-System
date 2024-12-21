package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.lesson.ILessonService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/lessons")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorLessonsController {
    private final ILessonService lessonService;


    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> createLesson(@PathVariable String course_id,@Valid @RequestBody LessonDto lessonDto) {
        return lessonService.createLesson(lessonDto, course_id);
    }

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getAllLessons(@PathVariable String course_id, @RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        return lessonService.getAllLessons(course_id, page, size);
    }

    @GetMapping("/{lesson_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getLessonById(@PathVariable String course_id,@PathVariable String lesson_id) {
        return lessonService.getLessonById(lesson_id);
    }

    @DeleteMapping("/{lesson_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> deleteLesson(@PathVariable String course_id,@PathVariable String lesson_id) {
        return lessonService.deleteLesson(lesson_id);
    }

    @PutMapping("/{lesson_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> updateLesson(@PathVariable String course_id,@PathVariable String lesson_id,@Valid @RequestBody LessonDto lessonDto) {
        return lessonService.updateLesson(lesson_id, lessonDto);
    }

}
