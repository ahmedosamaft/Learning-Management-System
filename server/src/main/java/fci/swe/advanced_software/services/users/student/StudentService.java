package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.course.EnrollmentDto;
import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.users.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentMapper studentMapper;

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
                .withData(studentDto)
                .build();
    }

    @Override
    public ResponseEntity<?> enrollCourse(EnrollmentDto enrollDto) {
        Student student = studentRepository.findById(enrollDto.getStudentId()).orElse(null);
        Course course = courseRepository.findById(enrollDto.getCourseId()).orElse(null);

        if (student == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Student not found!")
                    .build();
        }

        if (course == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Course not found!")
                    .build();
        }

        Enrollment enrollment = new Enrollment(student, course, null);
        student.getEnrollments().add(enrollment);
        studentRepository.save(student);
        enrollmentRepository.save(enrollment);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Course enrolled successfully!")
                .withData(enrollment)
                .build();
    }

    @Override
    public ResponseEntity<?> getGrades(String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCourses(String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> searchCourses(String keyword) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCourseProgress(String studentId, String courseId) {
        return null;
    }

    @Override
    public ResponseEntity<?> dropCourse(String studentId, String courseId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCourseLessons(String studentId, String courseId) {
        return null;
    }

    @Override
    public ResponseEntity<?> attendLesson(String studentId, String lessonId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAssessments(String studentId, AssessmentType type) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCourseAssessments(String studentId, String courseId, AssessmentType type) {
        return null;
    }

    @Override
    public ResponseEntity<?> attemptAssessment(String studentId, String assessmentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getFeedbacks(String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCourseFeedbacks(String studentId, String courseId) {
        return null;
    }

    @Override
    public ResponseEntity<?> requestFeedback(String studentId, String assessmentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getReports(String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> generateReport(String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> comment(String studentId, String announcementId, String comment) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateProfile(String studentId, StudentRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getNotifications(String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAttendanceRecord(String studentId) {
        return null;
    }

}
