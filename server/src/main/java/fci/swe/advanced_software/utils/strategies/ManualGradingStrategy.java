package fci.swe.advanced_software.utils.strategies;

import fci.swe.advanced_software.models.assessments.Answer;

public class ManualGradingStrategy implements IGradingStrategy {
    @Override
    public void grade(Answer answer) {
        answer.setGrade(null);
        // add to the manual grading queue
        // Manual grading will be done by the instructor
    }
}
