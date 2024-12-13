package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.submission.SubmissionRequestDto;
import fci.swe.advanced_software.dtos.assessments.submission.SubmissionResponseDto;
import fci.swe.advanced_software.models.assessments.Submission;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.SubmissionRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.SubmissionMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubmissionService implements ISubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssessmentRepository assessmentRepository;
    private final SubmissionMapper submissionMapper;

    @Override
    public ResponseEntity<?> createSubmission(SubmissionRequestDto requestDto) {
        Submission submission = submissionMapper.toEntity(requestDto);
        submission = submissionRepository.save(submission);

        SubmissionResponseDto responseDto = submissionMapper.toResponseDto(submission);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/submissions/" + submission.getId())
                .withData(responseDto)
                .withMessage("Submission created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getSubmission(String id) {
        Submission submission = submissionRepository.findById(id).orElse(null);

        if (submission == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Submission not found!")
                    .build();
        }

        SubmissionResponseDto responseDto = submissionMapper.toResponseDto(submission);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(responseDto)
                .withMessage("Submission found successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> deleteSubmission(String id) {
        Submission submission = submissionRepository.findById(id).orElse(null);

        if (submission == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Submission not found!")
                    .build();
        }

        submissionRepository.delete(submission);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Submission deleted successfully!")
                .build();
    }
}
