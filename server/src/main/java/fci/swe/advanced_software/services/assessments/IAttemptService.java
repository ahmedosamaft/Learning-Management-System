package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptRequestDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.users.Student;
import org.springframework.http.ResponseEntity;

public interface IAttemptService {

    ResponseEntity<?> createAttempt(AttemptRequestDto requestDto);


    ResponseEntity<?> updateAttempt(String id, AttemptRequestDto requestDto);

    ResponseEntity<?> getAttemptById(String id);

    ResponseEntity<?> getAttemptsByAssessment(Assessment assessment);

    ResponseEntity<?> getAttemptsByStudent(Student student);

    ResponseEntity<?> deleteAttempt(String id);
}
