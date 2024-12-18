package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackDto;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.assessments.Feedback;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class FeedbackMapper {

    protected StudentRepository studentRepository;
    protected AttemptRepository attemptRepository;
    protected InstructorRepository instructorRepository;

    @Mapping(target = "student", source = "studentId", qualifiedByName = "studentDtoToStudent")
    @Mapping(target = "attempt", source = "attemptId", qualifiedByName = "attemptDtoToAttempt")
    @Mapping(target = "instructor", source = "instructorId", qualifiedByName = "instructorDtoToInstructor")
    public abstract Feedback toEntity(FeedbackDto requestDto);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "attemptId", source = "attempt.id")
    @Mapping(target = "instructorId", source = "instructor.id")
    public abstract FeedbackDto toDto(Feedback feedback);

    @Named("studentDtoToStudent")
    public Student studentDtoToStudent(String studentId) {
        if (studentId == null) {
            return null;
        }
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
    }

    @Named("attemptDtoToAttempt")
    public Attempt attemptDtoToAttempt(String attemptId) {
        if (attemptId == null) {
            return null;
        }
        return attemptRepository.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attempt ID: " + attemptId));
    }

    @Named("instructorDtoToInstructor")
    public Instructor instructorDtoToInstructor(String instructorId) {
        if (instructorId == null) {
            return null;
        }
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid instructor ID: " + instructorId));
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    @Autowired
    public void setInstructorRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

}
