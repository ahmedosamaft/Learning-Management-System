package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.dtos.course.CourseSearchDto;
import fci.swe.advanced_software.dtos.course.EnrollmentDto;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.course.*;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.AttendanceMapper;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LessonRepository lessonRepository;
    private final CourseMapper courseMapper;
    private final AttendanceMapper attendanceMapper;
    private final AuthUtils authUtils;
    private final RepositoryUtils repositoryUtils;
    private final CourseSearchRepository courseSearchRepository;

    @Override
    public ResponseEntity<?> enrollCourse(String courseId, String studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!");
        }
        Course course = validateAndRetrieveCourse(courseId);

        if(enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("You are already enrolled in this course!")
                    .build();
        }

        EnrollmentDto enrollmentDto = EnrollmentDto.builder()
                .studentId(student.getId())
                .courseId(course.getId())
                .build();
        Enrollment enrollment = new Enrollment(student, course, null);
        enrollmentRepository.save(enrollment);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Course enrolled successfully!")
                .withData("enrollment", enrollmentDto)
                .build();
    }

    @Override
    public ResponseEntity<?> dropCourse(String courseId, String StudentId) {
        Student student = studentRepository.findById(StudentId).orElse(null);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!");
        }
        Course course = validateAndRetrieveCourse(courseId);

        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course);

        if (enrollment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Course not found!")
                    .build();
        }

        enrollmentRepository.delete(enrollment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Course dropped successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getCourses(Integer page, Integer size) {
        Student student = validateAndRetrieveCurrentStudent();
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");

        List<CourseDto> coursesDto = enrollmentRepository.findAllByStudent(student, pageable)
                .map(enrollment -> courseMapper.toDto(enrollment.getCourse()))
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("courses", coursesDto)
                .build();
    }

    @Override
    public ResponseEntity<?> searchCourses(String query, Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");

        Page<CourseSearchDto> courses = courseSearchRepository.searchAllByCodeOrNameOrDescription(query, query, query, pageable);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("courses", courses.getContent())
                .build();
    }

    @Override
    public ResponseEntity<?> attendLesson(String lessonId, String otp) {
        Student student = validateAndRetrieveCurrentStudent();
        Lesson lesson = validateAndRetrieveLesson(lessonId);
        if (attendanceRepository.existsByLessonIdAndStudentId(lessonId, student.getId())) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("You already attended this lesson!")
                    .build();
        }

        if (!lesson.getOtp().equals(otp)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("Invalid OTP!")
                    .build();
        }

        Attendance attendance = Attendance.builder()
                .student(student)
                .lesson(lesson)
                .course(lesson.getCourse())
                .attendedAt(Timestamp.from(Instant.now()))
                .build();

        attendance = attendanceRepository.save(attendance);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Lesson attended successfully!")
                .withData("attendance", attendanceMapper.toDto(attendance))
                .build();
    }

    private Student validateAndRetrieveCurrentStudent() {
        Student student = studentRepository.findById(authUtils.getCurrentUserId()).orElse(null);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a student!");
        }
        return student;
    }

    private Course validateAndRetrieveCourse(String courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found!");
        }
        return course;
    }

    private Lesson validateAndRetrieveLesson(String lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if (lesson == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found!");
        }
        return lesson;
    }
}
