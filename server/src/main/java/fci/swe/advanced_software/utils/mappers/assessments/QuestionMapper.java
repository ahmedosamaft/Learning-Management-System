package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class QuestionMapper {
    protected AssessmentRepository assessmentRepository;

    @Mapping(target = "assessment", source = "assessmentId", qualifiedByName = "assessmentDtoToAssessment")
    public abstract Question toEntity(QuestionRequestDto requestDto);

    @Mapping(target = "id", source = "question.id")
    @Mapping(target = "assessmentId", source = "assessment.id")
    public abstract QuestionResponseDto toResponseDto(Question question);

    @Named("assessmentDtoToAssessment")
    public Assessment assessmentDtoToAssessment(String assessmentId) {
        if (assessmentId == null) {
            return null;
        }
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID: " + assessmentId));
    }

    @Autowired
    protected void setAssessmentRepository(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }
}
