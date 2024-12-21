package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/quizzes")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuizzesController {
    private final IAssessmentService quizService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getAllQuizzes(@PathVariable String course_id, @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return quizService.getAllAssessments(course_id, AssessmentType.QUIZ, page, size);
    }

    @GetMapping("/{quiz_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getQuizById(@PathVariable String course_id, @PathVariable String quiz_id) {
        return quizService.getAssessment(quiz_id);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> createQuiz(@PathVariable String course_id, @Valid @RequestBody AssessmentDto quiz) {
        return quizService.createAssessment(course_id, AssessmentType.QUIZ, quiz);
    }

    @PutMapping("/{quiz_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> updateQuiz(@PathVariable String course_id, @PathVariable String quiz_id,
                                        @Valid @RequestBody AssessmentDto quiz) {
        return quizService.updateAssessment(quiz_id, AssessmentType.QUIZ, quiz);
    }

    @DeleteMapping("/{quiz_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> deleteQuiz(@PathVariable String course_id, @PathVariable String quiz_id) {
        return quizService.deleteAssessment(quiz_id);
    }
}
