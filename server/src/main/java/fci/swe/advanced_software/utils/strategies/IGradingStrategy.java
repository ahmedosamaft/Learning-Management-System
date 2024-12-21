package fci.swe.advanced_software.utils.strategies;

import fci.swe.advanced_software.models.assessments.Answer;

public interface IGradingStrategy {
    void grade(Answer answer);
}
