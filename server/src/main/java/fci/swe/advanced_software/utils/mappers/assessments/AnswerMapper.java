package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.dtos.assessments.answer.AnswerResponseDto;
import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AnswerMapper {
    protected StudentRepository studentRepository;
    protected AssessmentRepository assessmentRepository;
    protected QuestionRepository questionRepository;
    protected AttemptRepository attemptRepository;

    @Mapping(target = "student", source = "studentId", qualifiedByName = "studentDtoToStudent")
    @Mapping(target = "assessment", source = "assessmentId", qualifiedByName = "assessmentDtoToAssessment")
    @Mapping(target = "question", source = "questionId", qualifiedByName = "questionDtoToQuestion")
    @Mapping(target = "attempt", source = "attemptId", qualifiedByName = "attemptDtoToAttempt")
    public abstract Answer toEntity(AnswerRequestDto requestDto);


    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "assessmentId", source = "assessment.id")
    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "attemptId", source = "attempt.id")
    public abstract AnswerResponseDto toResponseDto(Answer answer);

    @Named("studentDtoToStudent")
    public Student studentDtoToStudent(String studentId) {
        if (studentId == null) {
            return null;
        }
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
    }

    @Named("assessmentDtoToAssessment")
    public Assessment assessmentDtoToAssessment(String assessmentId) {
        if (assessmentId == null) {
            return null;
        }
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID: " + assessmentId));
    }

    @Named("questionDtoToQuestion")
    public Question questionDtoToQuestion(String questionId) {
        if (questionId == null) {
            return null;
        }
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + questionId));
    }

    @Named("attemptDtoToAttempt")
    public Attempt attemptDtoToAttempt(String attemptId) {
        if (attemptId == null) {
            return null;
        }
        return attemptRepository.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attempt ID: " + attemptId));
    }

    @Autowired
    protected void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    protected void setAssessmentRepository(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @Autowired
    protected void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    protected void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }
}
