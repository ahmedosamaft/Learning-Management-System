package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.dtos.assessments.AssignmentResponseDto;
import fci.swe.advanced_software.models.courses.Course;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IAssignmentService {
    ResponseEntity<?> createAssignment(AssignmentRequestDto requestDto);

    ResponseEntity<?> updateAssignment(String id, AssignmentRequestDto requestDto);

    ResponseEntity<?> getAssignmentById(String id);

    ResponseEntity<?> getAssignmentsByCourse(Course course);

    ResponseEntity<?> deleteAssignment(String id);
}
