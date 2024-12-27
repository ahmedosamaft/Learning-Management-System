package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.assessments.QuestionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RepositoryUtils repositoryUtils;

    @InjectMocks
    private QuestionService questionService;

    private QuestionRequestDto questionRequestDto;
    private Question question;
    private QuestionResponseDto questionResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        questionRequestDto = QuestionRequestDto.builder()
                .courseId("course1")
                .text("What is 2+2?")
                .correctAnswer("4")
                .questionType(QuestionType.MCQ)
                .score(5)
                .build();

        question = new Question();
        question.setId("question1");
        question.setText("What is 2+2?");
        question.setCorrectAnswer("4");
        question.setQuestionType(QuestionType.MCQ);
        question.setScore(5);

        questionResponseDto = QuestionResponseDto.builder()
                .id("question1")
                .text("What is 2+2?")
                .correctAnswer("4")
                .questionType(QuestionType.MCQ)
                .score(5)
                .build();
    }

    @Test
    void QuestionService_CreateQuestion_ReturnsCreated() {
        when(courseRepository.findById("course1")).thenReturn(java.util.Optional.of(new fci.swe.advanced_software.models.courses.Course()));
        when(questionMapper.toEntity(questionRequestDto)).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toResponseDto(question)).thenReturn(questionResponseDto);

        ResponseEntity<?> response = questionService.createQuestion("course1", questionRequestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question created successfully!"));
    }

    @Test
    void QuestionService_UpdateQuestion_QuestionNotFound() {
        when(questionRepository.findById("question1")).thenReturn(java.util.Optional.empty());

        ResponseEntity<?> response = questionService.updateQuestion("course1", "question1", questionRequestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question not found!"));
    }

    @Test
    void QuestionService_UpdateQuestion_ReturnsUpdatedQuestion() {
        when(questionRepository.findById("question1")).thenReturn(java.util.Optional.of(question));
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toResponseDto(question)).thenReturn(questionResponseDto);

        ResponseEntity<?> response = questionService.updateQuestion("course1", "question1", questionRequestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question updated successfully!"));
    }

    @Test
    void QuestionService_GetQuestionById_QuestionNotFound() {
        when(questionRepository.findById("question1")).thenReturn(java.util.Optional.empty());

        ResponseEntity<?> response = questionService.getQuestionById("question1");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question not found!"));
    }

    @Test
    void QuestionService_GetQuestionById_ReturnsQuestion() {
        when(questionRepository.findById("question1")).thenReturn(java.util.Optional.of(question));
        when(questionMapper.toResponseDto(question)).thenReturn(questionResponseDto);

        ResponseEntity<?> response = questionService.getQuestionById("question1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question found successfully!"));
    }

    @Test
    void QuestionService_DeleteQuestion_QuestionNotFound() {
        when(questionRepository.findById("question1")).thenReturn(java.util.Optional.empty());

        ResponseEntity<?> response = questionService.deleteQuestion("question1");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question not found!"));
    }

    @Test
    void QuestionService_DeleteQuestion_ReturnsDeletedQuestion() {
        when(questionRepository.findById("question1")).thenReturn(java.util.Optional.of(question));

        ResponseEntity<?> response = questionService.deleteQuestion("question1");

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Question deleted successfully!"));
    }
}
