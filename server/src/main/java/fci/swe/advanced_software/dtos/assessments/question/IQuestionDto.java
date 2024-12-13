package fci.swe.advanced_software.dtos.assessments.question;

import fci.swe.advanced_software.models.assessments.QuestionType;

import java.util.Map;

public interface IQuestionDto {
    String getCorrectAnswer();
    QuestionType getQuestionType();
    Map<String, String> getOptions();
}
