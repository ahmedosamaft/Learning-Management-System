package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptResponseDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.assessments.AttemptMapper;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttemptServiceTests {

    @InjectMocks
    private AttemptService attemptService;

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AttemptMapper attemptMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private AuthUtils authUtils;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Test
    public void createAttempt_StudentFoundAndAssessmentFoundAndAssessmentNotAttemptedYet_ShouldReturnCreated() {

        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name(student.getName())
                .email(student.getEmail())
                .role(student.getRole())
                .build();

        Assessment assessment = Assessment.builder()
                .id(UUID.randomUUID().toString())
                .instructions("instructions")
                .type(AssessmentType.QUIZ)
                .build();

        Attempt attempt = Attempt.builder()
                .id(UUID.randomUUID().toString())
                .assessment(assessment)
                .student(student)
                .build();

        AttemptResponseDto attemptResponseDto = AttemptResponseDto.builder()
                .id(attempt.getId())
                .student(userResponseDto)
                .isGraded(false)
                .build();

        when(assessmentRepository.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        when(attemptRepository.existsByStudentIdAndAssessmentId(student.getId(), assessment.getId())).thenReturn(false);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(attemptRepository.save(any())).thenReturn(attempt);
        when(attemptMapper.toResponseDto(attempt)).thenReturn(attemptResponseDto);
        when(authUtils.getCurrentUserId()).thenReturn(student.getId());

        ResponseEntity<Response> response = attemptService.createAttempt(UUID.randomUUID().toString(), assessment.getType(), assessment.getId());

        verify(assessmentRepository, times(1)).findById(assessment.getId());
        verify(attemptRepository, times(1)).existsByStudentIdAndAssessmentId(student.getId(), assessment.getId());
        verify(studentRepository, times(1)).findById(student.getId());
        verify(attemptRepository, times(1)).save(any());
        verify(attemptMapper, times(1)).toResponseDto(attempt);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Attempt created successfully!", response.getBody().getMessage());
        assertEquals(Map.of("attempt", attemptResponseDto), response.getBody().getData());
        String location = Constants.API_VERSION + "/attempts/" + attempt.getId();
        assertEquals(location, response.getHeaders().getLocation().toString());
    }

    @Test
    public void createAttempt_StudentFoundAndAssessmentFoundAndAssessmentAttempted_ShouldReturnBadRequest() {
        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        Assessment assessment = Assessment.builder()
                .id(UUID.randomUUID().toString())
                .instructions("instructions")
                .type(AssessmentType.QUIZ)
                .build();

        when(attemptRepository.existsByStudentIdAndAssessmentId(student.getId(), assessment.getId())).thenReturn(true);
        when(authUtils.getCurrentUserId()).thenReturn(student.getId());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            attemptService.createAttempt(UUID.randomUUID().toString(), assessment.getType(), assessment.getId());
        });

        verify(assessmentRepository, never()).findById(assessment.getId());
        verify(attemptRepository, times(1)).existsByStudentIdAndAssessmentId(student.getId(), assessment.getId());
        verify(studentRepository, never()).findById(student.getId());
        verify(attemptRepository, never()).save(any());
        verify(attemptMapper, never()).toResponseDto(any());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("You already attempted this " + assessment.getType().name().toLowerCase() + "!", exception.getReason());
    }

    @Test
    public void createAttempt_StudentFoundAndAssessmentNotFound_ShouldReturnNotFound() {
        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        AssessmentType assessmentType = AssessmentType.QUIZ;
        String assessmentId = UUID.randomUUID().toString();

        when(attemptRepository.existsByStudentIdAndAssessmentId(student.getId(), assessmentId)).thenReturn(false);
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.empty());
        when(authUtils.getCurrentUserId()).thenReturn(student.getId());

        ResponseEntity<Response> response = attemptService.createAttempt(UUID.randomUUID().toString(), assessmentType, assessmentId);

        verify(attemptRepository, times(1)).existsByStudentIdAndAssessmentId(student.getId(), assessmentId);
        verify(assessmentRepository, times(1)).findById(assessmentId);
        verify(studentRepository, never()).findById(student.getId());
        verify(attemptRepository, never()).save(any());
        verify(attemptMapper, never()).toResponseDto(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(assessmentType.name().toLowerCase() + " not found!", response.getBody().getMessage());
    }

    @Test
    public void createAttempt_StudentNotFound_ShouldReturnNotFound() {
        Assessment assessment = Assessment.builder()
                .id(UUID.randomUUID().toString())
                .type(AssessmentType.QUIZ)
                .build();

        String studentId = UUID.randomUUID().toString();

        when(attemptRepository.existsByStudentIdAndAssessmentId(studentId, assessment.getId())).thenReturn(false);
        when(assessmentRepository.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        when(authUtils.getCurrentUserId()).thenReturn(studentId);

        ResponseEntity<Response> response = attemptService.createAttempt(UUID.randomUUID().toString(), assessment.getType(), assessment.getId());

        verify(attemptRepository, times(1)).existsByStudentIdAndAssessmentId(studentId, assessment.getId());
        verify(assessmentRepository, times(1)).findById(assessment.getId());
        verify(studentRepository, times(1)).findById(studentId);
        verify(attemptRepository, never()).save(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Student not found!", response.getBody().getMessage());
    }

    @Test
    public void getAttemptById_AttemptFound_ShouldReturnOk() {

        Attempt attempt = Attempt.builder()
                .id(UUID.randomUUID().toString())
                .build();

        AttemptResponseDto attemptResponseDto = AttemptResponseDto.builder()
                .id(attempt.getId())
                .isGraded(true)
                .build();

        when(attemptRepository.findById(attempt.getId())).thenReturn(Optional.of(attempt));
        when(attemptMapper.toResponseDto(attempt)).thenReturn(attemptResponseDto);

        ResponseEntity<Response> response = attemptService.getAttemptById(attempt.getId());

        verify(attemptRepository, times(1)).findById(attempt.getId());
        verify(attemptMapper, times(1)).toResponseDto(attempt);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Attempt retrieved successfully!", response.getBody().getMessage());
        assertEquals(Map.of("attempt", attemptResponseDto), response.getBody().getData());
    }

    @Test
    public void getAttemptById_AttemptNotFound_ShouldReturnNotFound() {
        String attemptId = UUID.randomUUID().toString();

        when(attemptRepository.findById(attemptId)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = attemptService.getAttemptById(attemptId);

        verify(attemptRepository, times(1)).findById(attemptId);
        verify(attemptMapper, never()).toResponseDto(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Attempt not found!", response.getBody().getMessage());
    }

    @Test
    public void getAttemptsByCourseIdAndStudentId_StudentFoundAndValidAssessmentType_ShouldReturnOk() {
        String courseId = UUID.randomUUID().toString();
        String studentId = UUID.randomUUID().toString();
        String assessmentType = AssessmentType.QUIZ.name().toLowerCase();
        Integer page = 1;
        Integer size = 10;


    }
}
