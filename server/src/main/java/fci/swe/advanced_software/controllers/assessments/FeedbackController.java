package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_VERSION + "/feedback")
@RequiredArgsConstructor
public class FeedbackController {
//    private final IFeedbackService feedbackService;

//    @PostMapping
//    public ResponseEntity<?> createFeedback(@Valid @RequestBody FeedbackDto feedbackDto) {
////        return feedbackService.createFeedback(feedbackDto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateFeedback(@PathVariable String id, @Valid @RequestBody FeedbackDto feedbackDto) {
//        return feedbackService.updateFeedback(id, feedbackDto);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getFeedback(@PathVariable String id) {
//        return feedbackService.getFeedback(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteFeedback(@PathVariable String id) {
//        return feedbackService.deleteFeedback(id);
//    }
}
