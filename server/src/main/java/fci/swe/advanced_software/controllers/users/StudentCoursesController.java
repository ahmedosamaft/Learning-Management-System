package fci.swe.advanced_software.controllers.users;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAnswerService;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
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
@RequestMapping(Constants.API_VERSION + "/students/courses/{course_id}")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentCoursesController {
    private final ILessonService lessonService;
    private final ICourseService courseService;
    private final IAssessmentService assessmentService;
    private final IStudentService studentService;
    private final IAttemptService attemptService;
    private final IAnswerService answerService;

    @PostMapping
    public ResponseEntity<?> enroll(@PathVariable String course_id) {
        return studentService.enrollCourse(course_id);
    }

    @GetMapping("/lessons")
    @PreAuthorize("@authorizationService.isEnrolled(#course_id)")
    public ResponseEntity<?> getLessons(@PathVariable String course_id,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return lessonService.getAllLessons(course_id, page, size);
    }

    @GetMapping("/lessons/{lesson_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#course_id)")
    public ResponseEntity<?> getLesson(@PathVariable String course_id, @PathVariable String lesson_id) {
        return lessonService.getLessonById(lesson_id);
    }

    @PostMapping("/lessons/{lesson_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#course_id)")
    public ResponseEntity<?> attendLesson(@PathVariable String course_id,
                                          @PathVariable String lesson_id,
                                          @RequestParam(name = "otp", defaultValue = "") String otp) {
        return studentService.attendLesson(lesson_id, otp);
    }

    @GetMapping("/assignments")
    @PreAuthorize("@authorizationService.isEnrolled(#course_id)")
    public ResponseEntity<?> getAssignments(@PathVariable String course_id,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return assessmentService.getAllAssessments(course_id, AssessmentType.ASSIGNMENT, page, size);
    }


    @PostMapping("/assignments/{assignment_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#course_id)")
    public ResponseEntity<?> startAssignmentAttempt(@PathVariable String course_id, @PathVariable String assignment_id) {
        return attemptService.createAttempt(course_id, AssessmentType.ASSIGNMENT, assignment_id);
    }

    @PostMapping("/assignments/{assignment_id}/attempts/{attempt_id}")
    @PreAuthorize("@authorizationService.isEnrolled(#course_id)")
    public ResponseEntity<?> submitAnswers(@PathVariable String course_id,
                                           @PathVariable String assignment_id,
                                           @PathVariable String attempt_id,
                                           @RequestBody @Valid List<AnswerRequestDto> answers) {

    return answerService.submitAnswers(
            course_id,
            assignment_id,
            AssessmentType.ASSIGNMENT,
            attempt_id,
            answers);
    }

}
