package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.QuestionRequestDto;
import org.springframework.http.ResponseEntity;

public interface IQuestionService {
    ResponseEntity<?> createQuestion(QuestionRequestDto requestDto);

    ResponseEntity<?> updateQuestion(String id, QuestionRequestDto requestDto);

    ResponseEntity<?> getQuestion(String id);

    ResponseEntity<?> deleteQuestion(String id);
}
