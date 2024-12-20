package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptResponseDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.AttemptMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttemptService implements IAttemptService {

    private final AttemptRepository attemptRepository;
    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final AttemptMapper attemptMapper;
    private final AuthUtils authUtils;

    @Override
    public ResponseEntity<?> createAttempt(String course_id, AssessmentType type, String assessment_id) {
        Assessment assessment = assessmentRepository
                .findById(assessment_id)
                .orElse(null);

        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage(type.name().toLowerCase() + " not found!")
                    .build();
        }

        Student student = studentRepository.findById(authUtils.getCurrentUserId()).orElse(null);
        if (student == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Student not found!")
                    .build();
        }

        Attempt attempt = Attempt
                .builder()
                .attemptedAt(Timestamp.from(Instant.now()))
                .assessment(assessment)
                .student(student)
                .build();


        attempt = attemptRepository.save(attempt);

        AttemptResponseDto responseDto = attemptMapper.toResponseDto(attempt);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/attempts/" + attempt.getId())
                .withData("attempt", responseDto)
                .withMessage("Attempt created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getAttemptById(String id) {
        Optional<Attempt> attemptOpt = attemptRepository.findById(id);
        if (attemptOpt.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Attempt not found!")
                    .build();
        }

        AttemptResponseDto responseDto = attemptMapper.toResponseDto(attemptOpt.get());

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attempt", responseDto)
                .build();
    }

    @Override
    public ResponseEntity<?> getAttemptsByStudent(Student student) {
        List<Attempt> attempts = attemptRepository.findByStudent(student);
        if (attempts.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("No attempts found for this student!")
                    .build();
        }

        List<AttemptResponseDto> responseDtos = attempts.stream()
                .map(attemptMapper::toResponseDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attempts", responseDtos)
                .build();
    }

    @Override
    public ResponseEntity<?> getAttemptsByAssessment(Assessment assessment) {
        List<Attempt> attempts = attemptRepository.findByAssessment(assessment);
        if (attempts.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("No attempts found for this assessment!")
                    .build();
        }

        List<AttemptResponseDto> responseDtos = attempts.stream()
                .map(attemptMapper::toResponseDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attempts", responseDtos)
                .build();
    }

    @Override
    public ResponseEntity<?> deleteAttempt(String id) {
        Optional<Attempt> attemptOpt = attemptRepository.findById(id);
        if (attemptOpt.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Attempt not found!")
                    .build();
        }

        attemptRepository.delete(attemptOpt.get());

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Attempt deleted successfully!")
                .build();
    }
}
