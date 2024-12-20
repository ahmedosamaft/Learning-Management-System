package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackDto;
import fci.swe.advanced_software.dtos.course.AttendanceDto;
import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.dtos.course.EnrollmentDto;
import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.assessments.FeedbackRepository;
import fci.swe.advanced_software.repositories.course.AttendanceRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.assessments.AttemptMapper;
import fci.swe.advanced_software.utils.mappers.assessments.FeedbackMapper;
import fci.swe.advanced_software.utils.mappers.courses.AttendanceMapper;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import fci.swe.advanced_software.utils.mappers.courses.LessonMapper;
import fci.swe.advanced_software.utils.mappers.users.StudentMapper;
import lombok.AllArgsConstructor;
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
    private final AttemptRepository attemptRepository;
    private final AssessmentRepository assessmentRepository;
    private final FeedbackRepository feedbackRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final AttendanceMapper attendanceMapper;
    private final LessonMapper lessonMapper;
    private final AttemptMapper attemptMapper;
    private final AuthUtils authUtils;
    private final FeedbackMapper feedbackMapper;


    @Override
    public ResponseEntity<?> getStudent(String id) {
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Student not found!")
                    .build();
        }

        StudentRequestDto studentDto = studentMapper.toDto(student);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("student", studentDto)
                .build();
    }

    @Override
    public ResponseEntity<?> enrollCourse(String courseId) {
        Student student = validateAndRetrieveCurrentStudent();
        Course course = validateAndRetrieveCourse(courseId);

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
    public ResponseEntity<?> getCourses() {
        Student student = validateAndRetrieveCurrentStudent();

        List<CourseDto> coursesDto = enrollmentRepository.findAllByStudent(student).stream()
                .map(enrollment -> courseMapper.toDto(enrollment.getCourse()))
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("courses", coursesDto)
                .build();
    }

    @Override
    public ResponseEntity<?> searchCourses(String keyword) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAttendance() {
        Student student = validateAndRetrieveCurrentStudent();

        List<AttendanceDto> attendanceDtos = attendanceRepository.findAllByStudent(student).stream()
                .map(attendanceMapper::toDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attendances", attendanceDtos)
                .build();
    }

    @Override
    public ResponseEntity<?> getCourseAttendance(String courseId) {
        Student student = validateAndRetrieveCurrentStudent();
        Course course = validateAndRetrieveCourse(courseId);

        List<AttendanceDto> attendanceDtos = attendanceRepository.findAllByStudentAndCourse(student, course)
                .stream()
                .map(attendanceMapper::toDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("attendances", attendanceDtos)
                .build();
    }

    @Override
    public ResponseEntity<?> attendLesson(String lessonId, String otp) {
        Student student = validateAndRetrieveCurrentStudent();
        Lesson lesson = validateAndRetrieveLesson(lessonId);
        if(attendanceRepository.existsByLessonIdAndStudentId(lessonId, student.getId())) {
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

    @Override
    public ResponseEntity<?> getFeedbacks(AssessmentType assessmentType) {
        Student student = validateAndRetrieveCurrentStudent();

        List<Attempt> attempts = attemptRepository.findByStudent(student);

        List<FeedbackDto> feedbacksDto = attempts.stream()
                .filter(
                        attempt -> attempt.getAssessment() != null &&
                                attempt.getAssessment().getType() == assessmentType &&
                                attempt.getFeedback() != null
                )
                .map(attempt -> feedbackMapper.toDto(attempt.getFeedback()))
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("feedbacks", feedbacksDto)
                .build();
    }

    @Override
    public ResponseEntity<?> getCourseFeedbacks(AssessmentType assessmentType, String courseId) {
        Student student = validateAndRetrieveCurrentStudent();
        Course course = validateAndRetrieveCourse(courseId);

        List<FeedbackDto> feedbacksDto = assessmentRepository.findAllByCourseAndType(course, assessmentType).stream()
                .map(assessment -> attemptRepository.findByAssessmentAndStudent(assessment, student))
                .filter(attempt -> attempt != null && attempt.getFeedback() != null)
                .map(attempt -> feedbackMapper.toDto(attempt.getFeedback()))
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("feedbacks", feedbacksDto)
                .build();
    }

    @Override
    public ResponseEntity<?> getReports() {
        return null;
    }

    @Override
    public ResponseEntity<?> comment(String announcementId, String comment) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateProfile(StudentRequestDto requestDto) {
        return null;
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
