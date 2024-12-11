package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.services.assessments.IAssignmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_VERSION + "/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final IAssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssignmentRequestDto assignment) {
        return assignmentService.createAssignment(assignment);
    }

}
