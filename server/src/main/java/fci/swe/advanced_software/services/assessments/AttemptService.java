package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptResponseDto;
import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptShortDto;
import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackUpdateDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.AttemptMapper;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class AttemptService implements IAttemptService {

    private final AttemptRepository attemptRepository;
    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final AttemptMapper attemptMapper;
    private final UserResponseMapper userResponseMapper;
    private final AuthUtils authUtils;
    private final RepositoryUtils repositoryUtils;

    @Override
    public ResponseEntity<Response> createAttempt(String courseId, AssessmentType type, String assessmentId) {
        if (attemptRepository.existsByStudentIdAndAssessmentId(authUtils.getCurrentUserId(), assessmentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already attempted this " + type.name().toLowerCase() + "!");
        }

        Assessment assessment = assessmentRepository
                .findById(assessmentId)
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
    public ResponseEntity<Response> getAttemptById(String id) {
        Attempt attempt = attemptRepository.findById(id).orElse(null);

        if (attempt == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Attempt not found!")
                    .build();
        }

        AttemptResponseDto responseDto = attemptMapper.toResponseDto(attempt);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Attempt retrieved successfully!")
                .withData("attempt", responseDto)
                .build();
    }

    public ResponseEntity<Response> oldGetAttemptsByCourseIdAndStudentId(String courseId, String studentId, Integer page, Integer size) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!");
        }
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Assessment> assessments = assessmentRepository.findAllByCourseId(courseId, pageable);

        List<Attempt> attempts = assessments
                .flatMap(assessment -> attemptRepository
                        .findAllByAssessmentAndStudentId(assessment, studentId).stream())
                .toList();

        List<AttemptResponseDto> responseDtos = attempts.stream()
                .map(attemptMapper::toResponseDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attempts", responseDtos)
                .build();
    }

    @Override
    public ResponseEntity<Response> getAttemptsByCourseIdAndStudentId(String courseId, String studentId, String type, Integer page, Integer size) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!");
        }
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Attempt> attempts;
        if (type != null) {
            AssessmentType assessmentType = switch (type) {
                case "assignment" -> AssessmentType.ASSIGNMENT;
                case "quiz" -> AssessmentType.QUIZ;
                default -> throw new IllegalArgumentException("Invalid assignment type: " + type);
            };
            attempts = attemptRepository.findAllByCourseIdAndStudentIdAndType(courseId, studentId, assessmentType, pageable);
        } else {
            attempts = attemptRepository.findAllByCourseIdAndStudentId(courseId, studentId, pageable);
        }
        List<AttemptResponseDto> response = attempts.stream()
                .map(attemptMapper::toResponseDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attempts", response)
                .build();
    }

    @Override
    public ResponseEntity<Response> updateAttempt(String id, FeedbackUpdateDto feedbackDto) {
        Attempt attempt = attemptRepository.findById(id).orElse(null);
        if (attempt == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Attempt not found!")
                    .build();
        }

        if (attempt.getFeedback() == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("Feedback not found!")
                    .build();
        }


        attempt.getFeedback().setComments(feedbackDto.getComments());
        attempt.getFeedback().setGrade(feedbackDto.getGrade());
        attempt = attemptRepository.save(attempt);

        AttemptResponseDto responseDto = attemptMapper.toResponseDto(attempt);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Feedback updated successfully!")
                .withData("attempt", responseDto)
                .build();
    }

    @Override
    public ResponseEntity<Response> getAttemptsByAssessmentId(String assessmentId, Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Attempt> attempts = attemptRepository.findAllByAssessmentId(assessmentId, pageable);

        List<AttemptShortDto> responseDtos = attempts
                .map(attempt -> new AttemptShortDto(attempt.getId(),
                        userResponseMapper.toDto(attempt.getStudent()),
                        (attempt.getFeedback() != null && attempt.getFeedback().getGrade() != null),
                        attempt.getAttemptedAt()
                ))
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attempts", responseDtos)
                .build();
    }

    @Override
    public ResponseEntity<Response> getAttemptByIdForStudent(String courseId, String attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt not found!"));
        if (!attempt.getAssessment().getCourse().getId().equals(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt not found!");
        }
        return getAttemptById(attemptId);
    }
}
