package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.QuestionMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final CourseRepository courseRepository;
    private final RepositoryUtils repositoryUtils;


    public ResponseEntity<?> createQuestion(String courseId, QuestionRequestDto requestDto) {
        Question question = questionMapper.toEntity(requestDto);

        question.setCourse(courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId)));

        question = questionRepository.save(question);

        QuestionResponseDto responseDto = questionMapper.toResponseDto(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/questions/" + question.getId())
                .withData("question", responseDto)
                .withMessage("Question created successfully!")
                .build();
    }

    public ResponseEntity<?> updateQuestion(String courseId, String id, QuestionRequestDto requestDto) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Question not found!")
                    .build();
        }

        if (requestDto.getCourseId() == null) {
            requestDto.setCourseId(courseId);
        }

        question = questionRepository.save(question);

        QuestionResponseDto responseDto = questionMapper.toResponseDto(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("question", responseDto)
                .withMessage("Question updated successfully!")
                .build();
    }

    public ResponseEntity<?> getQuestionById(String id) {
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
                .withData("question", responseDto)
                .withMessage("Question found successfully!")
                .build();
    }

    public ResponseEntity<?> deleteQuestion(String id) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Question not found!")
                    .build();
        }

        questionRepository.delete(question);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Question deleted successfully!")
                .build();
    }

    public ResponseEntity<?> getAllQuestions(String courseId, Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Question> questionsPage = questionRepository.findAllByCourseId(courseId, pageable);
        List<QuestionResponseDto> questions = questionsPage.map(questionMapper::toResponseDto).getContent();
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("questions", questions)
                .withMessage("questions" + " retrieved successfully!")
                .build();
    }

}
