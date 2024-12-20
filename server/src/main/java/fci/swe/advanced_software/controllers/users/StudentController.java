package fci.swe.advanced_software.controllers.users;

import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.users.student.IStudentService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Student related endpoints")
public class StudentController {
    private final IStudentService studentService;
    private final AuthUtils authUtils;

    @GetMapping("/{id}")
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/enroll/{id}")
    @RolesAllowed(Roles.STUDENT)
    public ResponseEntity<?> enrollCourse(@PathVariable String id) {
        return studentService.enrollCourse(id);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getCourses() {
        return studentService.getCourses();
    }

    @GetMapping("/courses/search")
    public ResponseEntity<?> searchCourses(@RequestParam String keyword) {
        return studentService.searchCourses(keyword);
    }

    @GetMapping("/attendance")
    public ResponseEntity<?> getAttendance() {
        return studentService.getAttendance();
    }

    @GetMapping("/attendance/{courseId}")
    public ResponseEntity<?> getCourseAttendance(@PathVariable String courseId) {
        return studentService.getCourseAttendance(courseId);
    }

    @PostMapping("/attendance/{lessonId}")
    public ResponseEntity<?> attendLesson(@PathVariable String lessonId, @RequestParam String otp) {
        return studentService.attendLesson(lessonId, otp);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<?> getFeedbacks(@RequestParam AssessmentType assessmentType) {
        return studentService.getFeedbacks(assessmentType);
    }

    @GetMapping("/feedbacks/{courseId}")
    public ResponseEntity<?> getCourseFeedbacks(@RequestParam AssessmentType assessmentType, @PathVariable String courseId) {
        return studentService.getCourseFeedbacks(assessmentType, courseId);
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getReports() {
        return studentService.getReports();
    }

    @PostMapping("/comment/{announcementId}")
    public ResponseEntity<?> comment(@PathVariable String announcementId, @RequestParam String comment) {
        return studentService.comment(announcementId, comment);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody StudentRequestDto studentRequestDto) {
        return studentService.updateProfile(studentRequestDto);
    }
}
