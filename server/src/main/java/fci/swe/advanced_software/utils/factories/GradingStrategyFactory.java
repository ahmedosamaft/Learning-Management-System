package fci.swe.advanced_software.utils.factories;

import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.GradingType;
import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.utils.strategies.AutomaticGradingStrategy;
import fci.swe.advanced_software.utils.strategies.IGradingStrategy;
import fci.swe.advanced_software.utils.strategies.ManualGradingStrategy;

public class GradingStrategyFactory {
    public static IGradingStrategy getGradingStrategy(Answer answer) {
        QuestionType questionType = answer.getQuestion().getQuestionType();
        if (answer.getAssessment().getType() == AssessmentType.ASSIGNMENT && questionType == QuestionType.SHORT_ANSWER) {
            answer.setGradingType(GradingType.MANUAL);
            return new ManualGradingStrategy();
        }
        answer.setGradingType(GradingType.AUTOMATIC);
        return new AutomaticGradingStrategy();
    }
}
