package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.QuestionAssessmentDto;
import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentQuestionsDto;
import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.Helper;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.AssessmentMapper;
import fci.swe.advanced_software.utils.mappers.assessments.AssessmentQuestionsMapper;
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
public class AssessmentService implements IAssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final CourseRepository courseRepository;
    private final RepositoryUtils repositoryUtils;
    private final Helper helper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AssessmentQuestionsMapper assessmentQuestionsMapper;

    @Override
    public ResponseEntity<?> getAllAssessments(String course_id, AssessmentType type, Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Assessment> assessmentsPage = assessmentRepository.findAllByCourseIdAndType(course_id, type, pageable);
        List<AssessmentDto> assessments = assessmentsPage.map(assessmentMapper::toResponseDto).getContent();
        String typeString = helper.getAssessmentTypePlural(type);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(typeString, assessments)
                .withMessage(typeString + " retrieved successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> createAssessment(String courseId, AssessmentType type, AssessmentDto requestDto) {
        requestDto.setCourseId(courseId);

        Assessment assessment = assessmentMapper.toEntity(requestDto);
        assessment.setType(type);
        assessment = assessmentRepository.save(assessment);

        AssessmentDto responseDto = assessmentMapper.toResponseDto(assessment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/assessments/" + assessment.getId())
                .withData("assessment", responseDto)
                .withMessage("Assessment created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> updateAssessment(String id, AssessmentType type, AssessmentDto requestDto) {
        Assessment assessment = assessmentRepository.findById(id).orElse(null);

        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }
        if (requestDto.getCourseId() == null) {
            requestDto.setCourseId(assessment.getCourse().getId());
        }

        assessment = assessmentRepository.save(assessment);
        AssessmentDto responseDto = assessmentMapper.toResponseDto(assessment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withLocation(Constants.API_VERSION + "/assessments/" + assessment.getId())
                .withData("assessment", responseDto)
                .withMessage("Assessment updated successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getAssessment(String id) {
        Assessment assessment = assessmentRepository.findById(id).orElse(null);

        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }

        AssessmentDto responseDto = assessmentMapper.toResponseDto(assessment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("assessment", responseDto)
                .build();
    }

    @Override
    public ResponseEntity<?> deleteAssessment(String id) {
        Assessment assessment = assessmentRepository.findById(id).orElse(null);

        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }

        assessmentRepository.delete(assessment);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> addQuestionsToAssessment(String assessmentId, List<QuestionAssessmentDto> questionAssessmentDtos) {
        Assessment assessment = assessmentRepository.findById(assessmentId).orElse(null);
        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }
        for (QuestionAssessmentDto questionAssessmentDto : questionAssessmentDtos) {
            Question question = questionRepository.findById(questionAssessmentDto.getQuestionId()).orElse(null);
            if (question == null) {
                return ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.NOT_FOUND)
                        .withMessage("Question not found!")
                        .build();
            }

            assessment.addQuestion(question);
        }
        assessment = assessmentRepository.save(assessment);

        AssessmentQuestionsDto assessmentQuestionsDto = assessmentQuestionsMapper.toResponseDto(assessment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Questions added successfully!")
                .withData("assessment", assessmentQuestionsDto)
                .build();
    }

    @Override
    public ResponseEntity<?> removeQuestionFromAssessment(String assessmentId, String questionId) {
        Assessment assessment = assessmentRepository.findById(assessmentId).orElse(null);
        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Question not found!")
                    .build();
        }
        if (!assessment.removeQuestion(question)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("Question not found in assessment!")
                    .build();
        }
        assessmentRepository.save(assessment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getQuestionsOfAssessment(String assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId).orElse(null);
        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }

        List<QuestionResponseDto> questions = assessment.getQuestions().stream().
                map(questionMapper::toResponseDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("questions", questions)
                .withMessage("Questions retrieved successfully!")
                .build();
    }
}
