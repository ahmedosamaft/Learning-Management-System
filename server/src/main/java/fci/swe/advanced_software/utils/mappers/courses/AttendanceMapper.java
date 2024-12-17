package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.AttendanceDto;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.course.AttendanceRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AttendanceMapper {
    protected AttendanceRepository attendanceRepository;
    private StudentRepository studentRepository;
    private LessonRepository lessonRepository;

    @Mapping(target = "student", source = "studentId", qualifiedByName = "studentDtoToStudent")
    @Mapping(target = "lesson", source = "lessonId", qualifiedByName = "lessonDtoToLesson")
    public abstract Attendance toEntity(AttendanceDto requestDto);

    @Mapping(target = "studentId", source = "id")
    @Mapping(target = "lessonId", source = "id")
    public abstract AttendanceDto toDto(Attendance attendance);

    @Named("studentDtoToStudent")
    public Student studentDtoToStudent(String studentId) {
        if (studentId == null) {
            return null;
        }
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
    }

    @Named("lessonDtoToLesson")
    public Lesson lessonDtoToLesson(String lessonId) {
        if (lessonId == null) {
            return null;
        }
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lesson ID: " + lessonId));
    }

    @Autowired
    protected void setAttendanceRepository(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
