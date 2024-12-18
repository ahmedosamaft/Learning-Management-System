package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptRequestDto;
import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptResponseDto;
import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class AttemptMapper {

    protected AssessmentRepository assessmentRepository;
    protected StudentRepository studentRepository;

    @Mapping(target = "assessment", source = "assessmentId", qualifiedByName = "assessmentDtoToAssessment")
    @Mapping(target = "student", source = "studentId", qualifiedByName = "studentDtoToStudent")
    @Mapping(target = "submissions", ignore = true)
    public abstract Attempt toEntity(AttemptRequestDto requestDto);

    @Mapping(target = "assessmentId", source = "assessment.id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "submissionIds", source = "submissions")
    public abstract AttemptResponseDto toResponseDto(Attempt attempt);

    @Named("assessmentDtoToAssessment")
    public Assessment assessmentDtoToAssessment(String assessmentId) {
        if (assessmentId == null) {
            return null;
        }
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID: " + assessmentId));
    }

    @Named("studentDtoToStudent")
    public Student studentDtoToStudent(String studentId) {
        if (studentId == null) {
            return null;
        }
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
    }

    public Set<String> submissionToSubmissionDto(Set<Answer> answerIds) {
        if (answerIds == null) {
            return null;
        }
        return answerIds.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
    }

    @Autowired
    protected void setAssessmentRepository(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @Autowired
    protected void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
