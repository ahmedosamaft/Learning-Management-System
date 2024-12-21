package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IQuestionService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/questions")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuestionsController {
    private final IQuestionService questionService;

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> createQuestion(@PathVariable String course_id, @Valid @RequestBody QuestionRequestDto questionDto) {
        return questionService.createQuestion(course_id, questionDto);
    }

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getAllQuestions(@PathVariable String course_id, @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return questionService.getAllQuestions(course_id, page, size);
    }

    @GetMapping("/{question_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getQuestionById(@PathVariable String course_id, @PathVariable String question_id) {
        return questionService.getQuestionById(question_id);
    }

    @DeleteMapping("/{question_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> deleteQuestion(@PathVariable String course_id, @PathVariable String question_id) {
        return questionService.deleteQuestion(question_id);
    }

    @PutMapping("/{question_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> updateQuestion(@PathVariable String course_id, @PathVariable String question_id, @Valid @RequestBody QuestionRequestDto questionDto) {
        return questionService.updateQuestion( course_id,question_id, questionDto);
    }
}
