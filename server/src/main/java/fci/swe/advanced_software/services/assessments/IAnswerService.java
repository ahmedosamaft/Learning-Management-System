package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import org.springframework.http.ResponseEntity;

public interface IAnswerService {
    ResponseEntity<?> createAnswer(AnswerRequestDto requestDto);

    ResponseEntity<?> getAnswer(String id);

    ResponseEntity<?> deleteAnswer(String id);
}
