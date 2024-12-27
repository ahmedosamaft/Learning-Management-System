package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.IMediaService;
import fci.swe.advanced_software.services.courses.ResourceType;
import fci.swe.advanced_software.services.courses.lesson.ILessonService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/lessons")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorLessonsController {
    private final ILessonService lessonService;
    private final IMediaService mediaService;

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> createLesson(@PathVariable @UUID String courseId,@Valid @RequestBody LessonDto lessonDto) {
        return lessonService.createLesson(lessonDto, courseId);
    }

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAllLessons(@PathVariable @UUID String courseId,
                                           @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                           @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size){
        return lessonService.getAllLessons(courseId, page, size);
    }

    @GetMapping("/{lessonId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getLessonById(@PathVariable @UUID String courseId, @PathVariable @UUID String lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteLesson(@PathVariable @UUID String courseId,@PathVariable @UUID String lessonId) {
        return lessonService.deleteLesson(lessonId);
    }

    @PutMapping("/{lessonId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateLesson(@PathVariable @UUID String courseId,@PathVariable @UUID String lessonId,@Valid @RequestBody LessonDto lessonDto) {
        return lessonService.updateLesson(lessonId, lessonDto);
    }

    @PostMapping("/{lessonId}/media")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> uploadMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("lessonId") String lessonId,
                                         @RequestParam("file") MultipartFile file) {
        return mediaService.uploadFile(lessonId, ResourceType.LESSON, file);
    }

    @DeleteMapping("/{lessonId}/media/{mediaId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("mediaId") String mediaId) {
        return mediaService.deleteFile(mediaId);
    }
}
