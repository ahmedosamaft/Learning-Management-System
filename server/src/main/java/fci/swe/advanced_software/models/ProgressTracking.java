package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_tracking")
public class ProgressTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private int quizzesCompleted;

    @Column(nullable = false)
    private int assignmentsSubmitted;

    @Column(nullable = false)
    private int attendanceCount;

    @Column(nullable = false)
    private int totalQuizzes;

    @Column(nullable = false)
    private int totalAssignments;

    @Column(nullable = false)
    private int totalAttendanceDays;

    @Column(nullable = false)
    private float quizCompletionPercentage;

    @Column(nullable = false)
    private float assignmentSubmissionPercentage;

    @Column(nullable = false)
    private float attendancePercentage;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    // Getters and setters
}
/*
 * Example of a CRON Job Logic (Pseudocode):
 * 
 * @Scheduled(cron = "0 0 1 * * *") // Runs once a day at midnight
 * public void updateProgressTracking() {
 * // Fetch all students and their courses
 * List<StudentCoursePair> studentCourses = fetchAllStudentCoursePairs();
 * 
 * for (StudentCoursePair pair : studentCourses) {
 * User student = pair.getStudent();
 * Course course = pair.getCourse();
 * 
 * // Fetch the number of quizzes, assignments, and attendance for the course
 * int totalQuizzes = countTotalQuizzes(course);
 * int totalAssignments = countTotalAssignments(course);
 * int totalAttendanceDays = countTotalAttendanceDays(course);
 * 
 * // Fetch the student's progress
 * int quizzesCompleted = countQuizzesCompleted(student, course);
 * int assignmentsSubmitted = countAssignmentsSubmitted(student, course);
 * int attendanceCount = countAttendance(student, course);
 * 
 * // Calculate percentages
 * float quizCompletionPercentage = (quizzesCompleted / (float) totalQuizzes) *
 * 100;
 * float assignmentSubmissionPercentage = (assignmentsSubmitted / (float)
 * totalAssignments) * 100;
 * float attendancePercentage = (attendanceCount / (float) totalAttendanceDays)
 * * 100;
 * 
 * // Update the ProgressTracking table
 * ProgressTracking progress = fetchProgressTrackingRecord(student, course);
 * progress.setQuizzesCompleted(quizzesCompleted);
 * progress.setAssignmentsSubmitted(assignmentsSubmitted);
 * progress.setAttendanceCount(attendanceCount);
 * progress.setQuizCompletionPercentage(quizCompletionPercentage);
 * progress.setAssignmentSubmissionPercentage(assignmentSubmissionPercentage);
 * progress.setAttendancePercentage(attendancePercentage);
 * progress.setLastUpdated(LocalDateTime.now());
 * 
 * save(progress);
 * }
 * }
 * 
 */