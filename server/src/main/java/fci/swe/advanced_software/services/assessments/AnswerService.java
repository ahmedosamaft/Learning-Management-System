package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.dtos.assessments.answer.AnswerResponseDto;
import fci.swe.advanced_software.models.assessments.*;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AnswerRepository;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.assessments.FeedbackRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.Helper;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.factories.GradingStrategyFactory;
import fci.swe.advanced_software.utils.mappers.assessments.AnswerMapper;
import fci.swe.advanced_software.utils.strategies.IGradingStrategy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@AllArgsConstructor
public class AnswerService implements IAnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final AssessmentRepository assessmentRepository;
    private final Helper helper;
    private final AttemptRepository attemptRepository;
    private final StudentRepository studentRepository;
    private final AuthUtils authUtils;
    private final CourseRepository courseRepository;
    private final FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public ResponseEntity<?> submitAnswers(String courseId, String assessmentId, AssessmentType assessmentType, String attemptId, List<AnswerRequestDto> answersDto) {
        Assessment assessment = findAssessment(assessmentId, assessmentType);
        Attempt attempt = findAttempt(attemptId);
        Student student = getCurrentStudent();

        validateSubmission(student, attempt);

        Course course = findCourse(courseId);

        List<Answer> answers = processAnswers(assessment, attempt, student, answersDto, assessmentType);

        AtomicInteger grade = new AtomicInteger();
        answers.forEach(answer -> grade.addAndGet(answer.getGrade()));

        Feedback feedback = createFeedback(attempt, student, course, assessmentType, grade.get());

        answerRepository.saveAll(answers);
        feedback = feedbackRepository.save(feedback);

        return buildResponse(assessmentType, feedback);
    }

    @Override
    public ResponseEntity<?> createAnswer(AnswerRequestDto requestDto) {
        Answer answer = answerMapper.toEntity(requestDto);
        IGradingStrategy gradingStrategy = GradingStrategyFactory.getGradingStrategy(answer);
        gradingStrategy.grade(answer);
        answer = answerRepository.save(answer);

        AnswerResponseDto responseDto = answerMapper.toResponseDto(answer);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/answer/" + answer.getId())
                .withData("answer", responseDto)
                .withMessage("Answer created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getAnswer(String id) {
        Answer answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Answer not found!")
                    .build();
        }

        AnswerResponseDto responseDto = answerMapper.toResponseDto(answer);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("answer", responseDto)
                .withMessage("Answer found successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> deleteAnswer(String id) {
        Answer answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Answer not found!")
                    .build();
        }

        answerRepository.delete(answer);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Answer deleted successfully!")
                .build();
    }

    private Assessment findAssessment(String assessmentId, AssessmentType assessmentType) {
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, assessmentType.name().toLowerCase() + " not found!"));
    }

    private Attempt findAttempt(String attemptId) {
        return attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt not found!"));
    }

    private Student getCurrentStudent() {
        return studentRepository.findById(authUtils.getCurrentUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!"));
    }

    private void validateSubmission(Student student, Attempt attempt) {
        if (answerRepository.existsByStudentAndAttempt(student, attempt)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have submitted for this attempt already!");
        }
        if (!attempt.getStudent().equals(student)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are NOT allowed to submit answers for this attempt!");
        }
    }

    private Course findCourse(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found!"));
    }

    private List<Answer> processAnswers(Assessment assessment, Attempt attempt, Student student, List<AnswerRequestDto> answersDto, AssessmentType assessmentType) {
        List<Answer> answers = new ArrayList<>();
        Set<Question> questions = assessment.getQuestions();
        for (int i = 0; i < answersDto.size(); i++) {
            Answer answer = answerMapper.toEntity(answersDto.get(i));
            if(!questions.contains(answer.getQuestion())) {
                log.warn("question {} is not in that assessment {{}}", answer.getQuestion().getId(), assessment.getId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question not found!");
            }
            answer.setAssessment(assessment);
            answer.setAttempt(attempt);
            answer.setStudent(student);

            IGradingStrategy gradingStrategy = GradingStrategyFactory.getGradingStrategy(answer);
            gradingStrategy.grade(answer);

            if (assessmentType == AssessmentType.QUIZ && answer.getGrade() == null) {
                log.error("Answer is null for a quiz! Check gradingStrategy");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error! Please contact the developer team.");
            }
            answers.add(answer);
        }
        return answers;
    }

    private Feedback createFeedback(Attempt attempt, Student student, Course course, AssessmentType assessmentType, int grade) {
        FeedbackType feedbackType = switch (assessmentType) {
            case QUIZ -> FeedbackType.AUTOMATIC;
            case ASSIGNMENT -> FeedbackType.MANUAL;
        };

        Feedback feedback = Feedback.builder()
                .attempt(attempt)
                .student(student)
                .course(course)
                .feedbackType(feedbackType)
                .build();

        // TBA AI set comment
        if (assessmentType == AssessmentType.QUIZ) {
            feedback.setGrade(grade);
            feedback.setComments("Automatic Generated Feedback!");
        }

        return feedback;
    }

    private ResponseEntity<?> buildResponse(AssessmentType assessmentType, Feedback feedback) {
        ResponseEntityBuilder responseEntityBuilder = ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Answers submitted successfully!");

        if (assessmentType == AssessmentType.QUIZ) {
            responseEntityBuilder.withData("feedback", feedback);
        }

        return responseEntityBuilder.build();
    }


}
