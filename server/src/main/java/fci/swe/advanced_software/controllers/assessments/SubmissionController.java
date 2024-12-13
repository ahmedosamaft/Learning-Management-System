package fci.swe.advanced_software.controllers.assessments;


import fci.swe.advanced_software.dtos.assessments.submission.SubmissionRequestDto;
import fci.swe.advanced_software.services.assessments.ISubmissionService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final ISubmissionService questionService;

    @PostMapping
    public ResponseEntity<?> createSubmission(@Valid @RequestBody SubmissionRequestDto questionRequestDto) {
        return questionService.createSubmission(questionRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubmission(@PathVariable String id) {
        return questionService.getSubmission(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubmission(@PathVariable String id) {
        return questionService.deleteSubmission(id);
    }
}
