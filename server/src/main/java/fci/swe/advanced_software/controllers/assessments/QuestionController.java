package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.QuestionRequestDto;
import fci.swe.advanced_software.services.assessments.IQuestionService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final IQuestionService questionService;

    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto) {
        return questionService.createQuestion(questionRequestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable String id, @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        return questionService.updateQuestion(id, questionRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable String id) {
        return questionService.getQuestion(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable String id) {
        return questionService.deleteQuestion(id);
    }
}
