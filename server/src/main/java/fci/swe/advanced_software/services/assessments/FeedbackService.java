package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackDto;
import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.assessments.Feedback;
import fci.swe.advanced_software.models.assessments.FeedbackType;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.assessments.FeedbackRepository;
import fci.swe.advanced_software.repositories.assessments.AnswerRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.FeedbackMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService implements IFeedbackService {

    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    private final AttemptRepository attemptRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final AnswerRepository answerRepository;

    @Override
    public ResponseEntity<?> createFeedback(FeedbackDto feedbackDto) {

        if (attemptRepository.findById(feedbackDto.getAttemptId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Attempt not found!")
                    .build();
        }

        if (!studentRepository.existsById(feedbackDto.getStudentId())) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Student not found!")
                    .build();
        }

        if (instructorRepository.findById(feedbackDto.getInstructorId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Instructor not found!")
                    .build();
        }

        Attempt attempt = attemptRepository.findById(feedbackDto.getAttemptId()).orElse(null);
        List<Answer> answers = answerRepository.findAllByAttempt(attempt);

        for (Answer answer : answers) {
            // Grade the submission
        }

        if (feedbackDto.getFeedbackType() == FeedbackType.AUTOMATIC) {
            // AI generate the feedback
        }


        Feedback feedback = feedbackMapper.toEntity(feedbackDto);
        feedback = feedbackRepository.save(feedback);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation("/feedbacks/" + feedback.getId())
                .withData("feedback", feedbackMapper.toDto(feedback))
                .withMessage("Feedback created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> updateFeedback(String id, FeedbackDto feedbackDto) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);

        if (feedback == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Feedback not found!")
                    .build();
        }

        if (attemptRepository.findById(feedbackDto.getAttemptId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Attempt not found!")
                    .build();
        }

        if (studentRepository.findById(feedbackDto.getStudentId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Student not found!")
                    .build();
        }

        if (instructorRepository.findById(feedbackDto.getInstructorId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Instructor not found!")
                    .build();
        }

        feedback = feedbackMapper.toEntity(feedbackDto);
        feedback = feedbackRepository.save(feedback);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withLocation("/feedbacks/" + feedback.getId())
                .withData("feedback", feedbackMapper.toDto(feedback))
                .withMessage("Feedback updated successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getFeedback(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found!"));

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("feedback", feedbackMapper.toDto(feedback))
                .withMessage("Feedback found successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> deleteFeedback(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found!"));

        feedbackRepository.delete(feedback);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Feedback deleted successfully!")
                .build();
    }

}
