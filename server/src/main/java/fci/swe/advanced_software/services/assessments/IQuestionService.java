package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import org.springframework.http.ResponseEntity;

public interface IQuestionService {
    ResponseEntity<?> createQuestion(String courseId, QuestionRequestDto requestDto);

    ResponseEntity<?> updateQuestion(String CourseId, String id, QuestionRequestDto requestDto);

    ResponseEntity<?> getAllQuestions(String courseId, Integer page, Integer size);

    ResponseEntity<?> getQuestionById(String id);

    ResponseEntity<?> deleteQuestion(String id);
}
