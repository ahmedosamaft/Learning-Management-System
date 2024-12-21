package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final IAttemptService attemptService;
    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;


    @GetMapping("/{id}")
    public ResponseEntity<?> getAttemptById(@PathVariable String id) {
        return attemptService.getAttemptById(id);
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<?> getAttemptsByAssessment(@PathVariable String assessmentId) {
        return attemptService.getAttemptsByAssessmentId(assessmentId);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttemptsByStudent(@PathVariable String studentId) {
        return attemptService.getAttemptsByStudentId(studentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttempt(@PathVariable String id) {
        return attemptService.deleteAttempt(id);
    }
}
