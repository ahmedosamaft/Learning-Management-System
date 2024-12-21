package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
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
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssessmentDto assignment) {
//        assignment.setType(AssessmentType.ASSIGNMENT);
        return assessmentService.createAssessment(assignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable String id) {
        return assessmentService.getAssessment(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable String id, @Valid @RequestBody AssessmentDto assignment) {
//        assignment.setType(AssessmentType.ASSIGNMENT);
        return assessmentService.updateAssessment(id, assignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable String id) {
        return assessmentService.deleteAssessment(id);
    }

}
