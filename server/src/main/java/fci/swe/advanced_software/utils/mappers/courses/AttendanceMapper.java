package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.AttendanceDto;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.course.AttendanceRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AttendanceMapper {
    protected AttendanceRepository attendanceRepository;
    protected StudentRepository studentRepository;
    protected LessonRepository lessonRepository;
    protected CourseRepository courseRepository;

    @Mapping(target = "student", source = "studentId", qualifiedByName = "studentDtoToStudent")
    @Mapping(target = "lesson", source = "lessonId", qualifiedByName = "lessonDtoToLesson")
    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseDtoToCourse")
    public abstract Attendance toEntity(AttendanceDto requestDto);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "courseId", source = "course.id")
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

    @Named("courseDtoToCourse")
    public Course courseDtoToCourse(String courseId) {
        if (courseId == null) {
            return null;
        }
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));
    }

    @Autowired
    protected void setAttendanceRepository(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
