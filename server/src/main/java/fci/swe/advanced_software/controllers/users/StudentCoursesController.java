package fci.swe.advanced_software.controllers.users;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAnswerService;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.services.courses.announcement.IAnnouncementService;
import fci.swe.advanced_software.services.courses.course.ICourseService;
import fci.swe.advanced_software.services.courses.lesson.ILessonService;
import fci.swe.advanced_software.services.users.student.IStudentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_VERSION + "/students/courses/{courseId}")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentCoursesController {
    private final ILessonService lessonService;
    private final ICourseService courseService;
    private final IAssessmentService assessmentService;
    private final IStudentService studentService;
    private final IAttemptService attemptService;
    private final IAnswerService answerService;
    private final IAnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<?> enroll(@PathVariable String courseId) {
        return studentService.enrollCourse(courseId);
    }

    @GetMapping("/lessons")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getLessons(@PathVariable String courseId,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return lessonService.getAllLessons(courseId, page, size);
    }

    @GetMapping("/assignments")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAssignments(@PathVariable String courseId,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return assessmentService.getAllAssessments(courseId, AssessmentType.ASSIGNMENT, page, size);
    }

    @GetMapping("/quizzes")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getQuizzes(@PathVariable String courseId,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return assessmentService.getAllAssessments(courseId, AssessmentType.QUIZ, page, size);
    }

    @GetMapping("/announcements")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAnnouncements(@PathVariable String courseId,
                                              @RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        return announcementService.getAnnouncements(courseId, page, size);
    }

    @GetMapping("/announcements/{announcement_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAnnouncement(@PathVariable String courseId, @PathVariable String announcement_id) {
        return announcementService.getAnnouncement(announcement_id);
    }

    @GetMapping("/lessons/{lesson_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getLesson(@PathVariable String courseId, @PathVariable String lesson_id) {
        return lessonService.getLessonById(lesson_id);
    }

    @PostMapping("/lessons/{lesson_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> attendLesson(@PathVariable String courseId,
                                          @PathVariable String lesson_id,
                                          @RequestParam(name = "otp", defaultValue = "") String otp) {
        return studentService.attendLesson(lesson_id, otp);
    }

    @PostMapping("/assignments/{assignment_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> startAssignmentAttempt(@PathVariable String courseId, @PathVariable String assignment_id) {
        return attemptService.createAttempt(courseId, AssessmentType.ASSIGNMENT, assignment_id);
    }

    @PostMapping("/quizzes/{quiz_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> startQuizAttempt(@PathVariable String courseId, @PathVariable String quiz_id) {
        return attemptService.createAttempt(courseId, AssessmentType.QUIZ, quiz_id);
    }

    @PostMapping("/assignments/{assignment_id}/attempts/{attempt_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> submitAssignmentAnswers(@PathVariable String courseId,
                                           @PathVariable String assignment_id,
                                           @PathVariable String attempt_id,
                                           @RequestBody @Valid List<AnswerRequestDto> answers) {

    return answerService.submitAnswers(
            courseId,
            assignment_id,
            AssessmentType.ASSIGNMENT,
            attempt_id,
            answers);
    }

    @PostMapping("/quizzes/{quiz_id}/attempts/{attempt_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> submitQuizAnswers(@PathVariable String courseId,
                                                     @PathVariable String quiz_id,
                                                     @PathVariable String attempt_id,
                                                     @RequestBody @Valid List<AnswerRequestDto> answers) {

        return answerService.submitAnswers(
                courseId,
                quiz_id,
                AssessmentType.QUIZ,
                attempt_id,
                answers);
    }
}
