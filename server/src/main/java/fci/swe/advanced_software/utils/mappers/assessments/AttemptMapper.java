package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptRequestDto;
import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptResponseDto;
import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AttemptMapper {

    protected AssessmentRepository assessmentRepository;
    protected StudentRepository studentRepository;

    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "assessment", ignore = true)
    public abstract Attempt toEntity(AttemptRequestDto requestDto);

    public abstract AttemptResponseDto toResponseDto(Attempt attempt);

    public Set<String> answerToAnswerDto(Set<Answer> answerIds) {
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
