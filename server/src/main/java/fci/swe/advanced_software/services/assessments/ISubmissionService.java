package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.submission.SubmissionRequestDto;
import org.springframework.http.ResponseEntity;

public interface ISubmissionService {
    ResponseEntity<?> createSubmission(SubmissionRequestDto requestDto);

    ResponseEntity<?> getSubmission(String id);

    ResponseEntity<?> deleteSubmission(String id);
}
