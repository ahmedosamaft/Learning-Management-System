package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IQuestionService;
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
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/questions")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuestionsController {
    private final IQuestionService questionService;
    private final IMediaService mediaService;

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> createQuestion(@PathVariable String courseId, @Valid @RequestBody QuestionRequestDto questionDto) {
        return questionService.createQuestion(courseId, questionDto);
    }

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAllQuestions(@PathVariable String courseId, @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return questionService.getAllQuestions(courseId, page, size);
    }

    @GetMapping("/{questionId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getQuestionById(@PathVariable String courseId, @PathVariable String questionId) {
        return questionService.getQuestionById(questionId);
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteQuestion(@PathVariable String courseId, @PathVariable String questionId) {
        return questionService.deleteQuestion(questionId);
    }

    @PutMapping("/{questionId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateQuestion(@PathVariable String courseId, @PathVariable String questionId, @Valid @RequestBody QuestionRequestDto questionDto) {
        return questionService.updateQuestion( courseId,questionId, questionDto);
    }

    @PostMapping("/{questionId}/media")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> uploadMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("questionId") String questionId,
                                         @RequestParam("file") MultipartFile file) {
        return mediaService.uploadFile(questionId, ResourceType.QUESTION, file);
    }

    @DeleteMapping("/{questionId}/media/{mediaId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("mediaId") String mediaId) {
        return mediaService.deleteFile(mediaId);
    }
}
