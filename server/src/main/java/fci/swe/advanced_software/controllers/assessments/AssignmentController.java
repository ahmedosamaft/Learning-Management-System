package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.services.assessments.IAssignmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final IAssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssignmentRequestDto assignment) {
        return assignmentService.createAssignment(assignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable String id) {
        return assignmentService.getAssignment(id);
    }

}
