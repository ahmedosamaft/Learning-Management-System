package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.dtos.assessments.answer.AnswerResponseDto;
import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.repositories.assessments.AnswerRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.factories.GradingStrategyFactory;
import fci.swe.advanced_software.utils.mappers.assessments.AnswerMapper;
import fci.swe.advanced_software.utils.strategies.IGradingStrategy;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerService implements IAnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Override
    public ResponseEntity<?> createAnswer(AnswerRequestDto requestDto) {
        Answer answer = answerMapper.toEntity(requestDto);
        IGradingStrategy gradingStrategy = GradingStrategyFactory.getGradingStrategy(answer);
        gradingStrategy.grade(answer);
        answer = answerRepository.save(answer);

        AnswerResponseDto responseDto = answerMapper.toResponseDto(answer);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/answer/" + answer.getId())
                .withData("answer", responseDto)
                .withMessage("Answer created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getAnswer(String id) {
        Answer answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Answer not found!")
                    .build();
        }

        AnswerResponseDto responseDto = answerMapper.toResponseDto(answer);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("answer", responseDto)
                .withMessage("Answer found successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> deleteAnswer(String id) {
        Answer answer = answerRepository.findById(id).orElse(null);

        if (answer == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Answer not found!")
                    .build();
        }

        answerRepository.delete(answer);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Answer deleted successfully!")
                .build();
    }
}
