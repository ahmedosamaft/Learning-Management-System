package fci.swe.advanced_software.controllers.assessments;
import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptRequestDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<?> createAttempt(@Valid @RequestBody AttemptRequestDto attemptRequestDto) {
        return attemptService.createAttempt(attemptRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAttemptById(@PathVariable String id) {
        return attemptService.getAttemptById(id);
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<?> getAttemptsByAssessment(@PathVariable String assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
        return attemptService.getAttemptsByAssessment(assessment);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttemptsByStudent(@PathVariable String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return attemptService.getAttemptsByStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttempt(@PathVariable String id) {
        return attemptService.deleteAttempt(id);
    }
}
