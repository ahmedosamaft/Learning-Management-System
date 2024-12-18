package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final IAssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<?> createQuiz(@Valid @RequestBody AssessmentDto quiz) {
        quiz.setType(AssessmentType.QUIZ);
        return assessmentService.createAssessment(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable String id) {
        return assessmentService.getAssessment(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable String id, @Valid @RequestBody AssessmentDto quiz) {
        quiz.setType(AssessmentType.QUIZ);
        return assessmentService.updateAssessment(id, quiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable String id) {
        return assessmentService.deleteAssessment(id);
    }
}
