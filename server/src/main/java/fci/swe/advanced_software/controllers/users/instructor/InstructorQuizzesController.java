package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.services.courses.IMediaService;
import fci.swe.advanced_software.services.courses.ResourceType;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/quizzes")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuizzesController {
    private final IAssessmentService quizService;
    private final IMediaService mediaService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAllQuizzes(@PathVariable String courseId, @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return quizService.getAllAssessments(courseId, AssessmentType.QUIZ, page, size);
    }

    @GetMapping("/{quizId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getQuizById(@PathVariable String courseId, @PathVariable String quizId) {
        return quizService.getAssessment(quizId);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> createQuiz(@PathVariable String courseId, @Valid @RequestBody AssessmentDto quiz) {
        return quizService.createAssessment(courseId, AssessmentType.QUIZ, quiz);
    }

    @PutMapping("/{quizId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateQuiz(@PathVariable String courseId, @PathVariable String quizId,
                                        @Valid @RequestBody AssessmentDto quiz) {
        return quizService.updateAssessment(quizId, AssessmentType.QUIZ, quiz);
    }

    @DeleteMapping("/{quizId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteQuiz(@PathVariable String courseId, @PathVariable String quizId) {
        return quizService.deleteAssessment(quizId);
    }

    @PostMapping("/{quizId}/media")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> uploadMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("quizId") String quizId,
                                         @RequestParam("file") MultipartFile file) {
        return mediaService.uploadFile(quizId, ResourceType.ASSESSMENT, file);
    }

    @DeleteMapping("/{quizId}/media/{mediaId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("mediaId") String mediaId) {
        return mediaService.deleteFile(mediaId);
    }
}
