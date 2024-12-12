package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.dtos.assessments.AssignmentResponseDto;
import fci.swe.advanced_software.models.assessments.Assignment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.assessments.AssignmentRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.AssignmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssignmentService implements IAssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    public ResponseEntity<?> createAssignment(AssignmentRequestDto requestDto) {
        Assignment assignment = assignmentMapper.toEntity(requestDto);
        assignment = assignmentRepository.save(assignment);

        AssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/assignments/" + assignment.getId())
                .withData(responseDto)
                .withMessage("Assignment created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> updateAssignment(String id, AssignmentRequestDto requestDto) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        if (assignment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assignment not found!")
                    .build();
        }

        assignment.setInstructions(requestDto.getInstructions());
        assignment.setMaxScore(requestDto.getMaxScore());
        assignment.setStartsAt(requestDto.getStartsAt());
        assignment.setEndsAt(requestDto.getEndsAt());


        assignmentRepository.save(assignment);

        AssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(responseDto)
                .withMessage("Assignment updated successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getAssignment(String id) {
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(id);

        if (assignmentOpt.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assignment not found!")
                    .build();
        }

        AssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignmentOpt.get());

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(responseDto)
                .build();
    }

    @Override
    public ResponseEntity<?> getAssignmentsByCourse(Course course) {
        List<Assignment> assignments = assignmentRepository.findByCourse(course);

        if (assignments.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("No assignments found for this course!")
                    .build();
        }

        List<AssignmentResponseDto> responseDtos = assignments.stream()
                .map(assignmentMapper::toResponseDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(responseDtos)
                .build();
    }

    @Override
    public ResponseEntity<?> deleteAssignment(String id) {
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(id);

        if (assignmentOpt.isEmpty()) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Assignment not found!")
                    .build();
        }

        assignmentRepository.delete(assignmentOpt.get());

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Assignment deleted successfully!")
                .build();
    }
}
