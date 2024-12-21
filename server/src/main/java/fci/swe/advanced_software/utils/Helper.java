package fci.swe.advanced_software.utils;

import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.stereotype.Service;

@Service
public class Helper {

    public String getAssessmentTypePlural(AssessmentType type) {
        return switch (type) {
            case ASSIGNMENT -> "Assignments";
            case QUIZ -> "Quizzes";
        };
    }
}
