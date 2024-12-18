package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackDto;
import org.springframework.http.ResponseEntity;

public interface IFeedbackService {
    ResponseEntity<?> createFeedback(FeedbackDto feedbackDto);

    ResponseEntity<?> updateFeedback(String id, FeedbackDto feedbackDto);

    ResponseEntity<?> getFeedback(String id);

    ResponseEntity<?> deleteFeedback(String id);
}
