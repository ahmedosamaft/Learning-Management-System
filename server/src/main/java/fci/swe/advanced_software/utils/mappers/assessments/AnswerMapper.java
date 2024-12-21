package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.dtos.assessments.answer.AnswerResponseDto;
import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AnswerMapper {
    protected QuestionRepository questionRepository;

    @Mapping(target = "question", source = "questionId", qualifiedByName = "questionDtoToQuestion")
    public abstract Answer toEntity(AnswerRequestDto requestDto);


    @Mapping(target = "questionId", source = "question.id")
    public abstract AnswerResponseDto toResponseDto(Answer answer);

    @Named("questionDtoToQuestion")
    public Question questionDtoToQuestion(String questionId) {
        if (questionId == null) {
            return null;
        }
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + questionId));
    }


    @Autowired
    protected void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
