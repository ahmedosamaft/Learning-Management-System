package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.QuestionRequestDto;
import fci.swe.advanced_software.dtos.assessments.QuestionResponseDto;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.QuestionMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public ResponseEntity<?> createQuestion(QuestionRequestDto requestDto) {
        Question question = questionMapper.toEntity(requestDto);
        question = questionRepository.save(question);

        QuestionResponseDto responseDto = questionMapper.toResponseDto(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/questions/" + question.getId())
                .withData(responseDto)
                .withMessage("Question created successfully!")
                .build();
    }

    public ResponseEntity<?> updateQuestion(String id, QuestionRequestDto requestDto){
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Question not found!")
                    .build();
        }

        question.setQuestionType(requestDto.getQuestionType());
        question.setOptions(requestDto.getOptions());
        question.setCorrectAnswer(requestDto.getCorrectAnswer());

        questionRepository.save(question);

        QuestionResponseDto responseDto = questionMapper.toResponseDto(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(responseDto)
                .withMessage("Question updated successfully!")
                .build();
    }

    public ResponseEntity<?> getQuestion(String id){
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Question not found!")
                    .build();
        }

        QuestionResponseDto responseDto = questionMapper.toResponseDto(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(responseDto)
                .build();
    }

    public ResponseEntity<?> deleteQuestion(String id){
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Question not found!")
                    .build();
        }

        questionRepository.delete(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Question deleted successfully!")
                .build();
    }
}
