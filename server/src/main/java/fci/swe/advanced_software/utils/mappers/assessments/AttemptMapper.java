package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.AttemptRequestDto;
import fci.swe.advanced_software.dtos.assessments.AttemptResponseDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import org.mapstruct.*;
@Mapper(componentModel = "spring", uses = {AssessmentRepository.class, StudentRepository.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AttemptMapper {

    @Mapping(target = "assessment", expression = "java(mapAssessment(requestDto.getAssessmentId(), assessmentRepository))")
    @Mapping(target = "student", expression = "java(mapStudent(requestDto.getStudentId(), studentRepository))")
    @Mapping(target = "submissions", ignore = true)
    Attempt toEntity(AttemptRequestDto requestDto, AssessmentRepository assessmentRepository, StudentRepository studentRepository);

    @Mapping(target = "assessmentId", source = "assessment.id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "submissionIds", expression = "java(attempt.getSubmissions().stream().map(submission -> submission.getId()).collect(Collectors.toSet()))")
    AttemptResponseDto toResponseDto(Attempt attempt);

    default Assessment mapAssessment(String assessmentId, AssessmentRepository assessmentRepository) {
        if (assessmentId == null) {
            return null;
        }
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID: " + assessmentId));
    }

    default Student mapStudent(String studentId, StudentRepository studentRepository) {
        if (studentId == null) {
            return null;
        }
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
    }
}
