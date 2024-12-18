package fci.swe.advanced_software.controllers.assessments;


import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.services.assessments.IAnswerService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final IAnswerService questionService;

    @PostMapping
    public ResponseEntity<?> createAnswer(@Valid @RequestBody AnswerRequestDto questionRequestDto) {
        return questionService.createAnswer(questionRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnswer(@PathVariable String id) {
        return questionService.getAnswer(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable String id) {
        return questionService.deleteAnswer(id);
    }
}
