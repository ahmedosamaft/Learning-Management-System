package fci.swe.advanced_software.utils.strategies;

import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Question;

public class AutomaticGradingStrategy implements IGradingStrategy {
    @Override
    public void grade(Answer answer) {
        Question question = answer.getQuestion();
        String correctAnswer = question.getCorrectAnswer().toLowerCase();
        String ans = answer.getAnswer().toLowerCase();
        // Add AI for Correcting Short answer
        if (ans.equals(correctAnswer)) {
            answer.setGrade(question.getScore());
        } else {
            answer.setGrade(0);
        }
    }
}
