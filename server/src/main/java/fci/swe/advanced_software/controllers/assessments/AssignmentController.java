package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final IAssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssessmentRequestDto assignment) {
        assignment.setType(AssessmentType.ASSIGNMENT);
        return assessmentService.createAssessment(assignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable String id) {
        return assessmentService.getAssessment(id);
    }

}
