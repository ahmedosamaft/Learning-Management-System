package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.AssessmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssessmentService implements IAssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final CourseRepository courseRepository;


    @Override
    public ResponseEntity<?> createAssessment(AssessmentDto requestDto) {

        if (courseRepository.findById(requestDto.getCourseId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Course not found!")
                    .build();
        }

        Assessment assessment = assessmentMapper.toEntity(requestDto);
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
    public ResponseEntity<?> updateAssessment(String id, AssessmentDto requestDto) {
        Assessment assessment = assessmentRepository.findById(id).orElse(null);

        if (assessment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assessment not found!")
                    .build();
        }

        if (courseRepository.findById(requestDto.getCourseId()).isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Course not found!")
                    .build();
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

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Assessment deleted successfully!")
                .build();
    }
}
