package fci.swe.advanced_software.controllers.course;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.lesson.ILessonService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lessons")
@AllArgsConstructor
public class LessonController {

    private final ILessonService lessonService;



    @GetMapping("/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable String id) {
        return lessonService.getLessonById(id);
    }


    @PutMapping("/{id}")
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> updateLesson(@PathVariable String id, @Valid @RequestBody LessonDto lessonDto) {
        return lessonService.updateLesson(id, lessonDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> deleteLesson(@PathVariable String id) {
        return lessonService.deleteLesson(id);
    }
}
