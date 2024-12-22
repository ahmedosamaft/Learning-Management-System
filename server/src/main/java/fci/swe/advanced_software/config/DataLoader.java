package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.assessments.*;
import fci.swe.advanced_software.models.courses.*;
import fci.swe.advanced_software.models.users.*;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.course.*;
import fci.swe.advanced_software.repositories.users.AdminRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.random.RandomGenerator;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final MediaRepository mediaRepository;
    private final AnnouncementRepository announcementRepository;
    private final CommentRepository commentRepository;
    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;
    private InstructorRepository instructorRepository;
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private AssessmentRepository assessmentRepository;
    private AttemptRepository attemptRepository;
    private QuestionRepository questionRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void loadData() {
        loadAdmins();
        loadInstructors();
        loadStudents();
        loadCourses();
    }


    private void loadAdmins() {
        if (!adminRepository.existsByEmail("abdelhady@fcai.admin.com")) {
            adminRepository.save(Admin.builder()
                    .name("Abdelhady")
                    .email("abdelhady@fcai.admin.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.ADMIN)
                    .build());
        }
    }

    private void loadInstructors() {
        if (!instructorRepository.existsByEmail("ahmed@fcai.instructor.com")) {
            instructorRepository.save(Instructor.builder()
                    .name("Ahmed")
                    .email("ahmed@fcai.instructor.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.INSTRUCTOR)
                    .build());
        }

        if (!instructorRepository.existsByEmail("tamer@fcai.instructor.com")) {
            instructorRepository.save(Instructor.builder()
                    .name("Tamer")
                    .email("tamer@fcai.instructor.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.INSTRUCTOR)
                    .build());
        }
    }

    private void loadStudents() {
        if (!studentRepository.existsByEmail("pop@fcai.student.com")) {
            studentRepository.save(Student.builder()
                    .name("pop")
                    .email("pop@fcai.student.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.STUDENT)
                    .build());
        }

        if (!studentRepository.existsByEmail("sayed@fcai.student.com")) {
            studentRepository.save(Student.builder()
                    .name("sayed")
                    .email("sayed@fcai.student.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.STUDENT)
                    .build());
        }
    }

    private void loadCourses() {
        if (courseRepository.count() == 0) {
            Course softwareEngineering = courseRepository.save(Course.builder()
                    .code("CS321")
                    .instructor(
                            instructorRepository
                                    .findByEmail("ahmed@fcai.instructor.com").orElseThrow(() -> new RuntimeException("NO INSTRUCTOR FOUND"))
                    )
                    .name("Software Engineering")
                    .description("This course introduces students to the principles of software engineering.")
                    .build()
            );

            Course os = courseRepository.save(Course.builder()
                    .code("CS316")
                    .instructor(
                            instructorRepository
                                    .findByEmail("ahmed@fcai.instructor.com").orElseThrow(() -> new RuntimeException("NO INSTRUCTOR FOUND"))
                    )
                    .name("OS")
                    .description("This course introduces students to the principles of OS.")
                    .build()
            );

            Course oop = courseRepository.save(Course.builder()
                    .code("CS311")
                    .instructor(
                            instructorRepository
                                    .findByEmail("tamer@fcai.instructor.com").orElseThrow(() -> new RuntimeException("NO INSTRUCTOR FOUND"))
                    )
                    .name("OOP")
                    .description("This course introduces students to the principles of OOP.")
                    .build()
            );

            loadLessonsToCourse(softwareEngineering);
            loadLessonsToCourse(oop);
            loadLessonsToCourse(os);

            loadQuestionsForCourse(softwareEngineering);
            loadQuestionsForCourse(oop);
            loadQuestionsForCourse(os);

            loadAssessmentsForCourse(softwareEngineering);
            loadAssessmentsForCourse(oop);
            loadAssessmentsForCourse(os);


            loadAnnouncementsForCourse(softwareEngineering);
            loadAnnouncementsForCourse(oop);
            loadAnnouncementsForCourse(os);
        }
    }

    private void loadLessonsToCourse(Course course) {
        for (int i = 1; i <= 5; i++) {
            Lesson lesson = lessonRepository.save(Lesson.builder()
                    .title("Lesson " + i + " for " + course.getName())
                    .content("This is the content of Lesson " + i + " for the course " + course.getName() + ".")
                    .otp(course.getCode().toLowerCase() + i)
                    .course(course)
                    .build());
            Media media = Media
                    .builder()
                    .lesson(lesson)
                    .realName("Video " + i)
                    .url("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                    .build();
            mediaRepository.save(media);
            lessonRepository.flush();
            mediaRepository.flush();
        }
    }

    private void loadQuestionsForCourse(Course course) {
        // Create 5 MCQ questions
        String[] options = {"A", "B", "C", "D"};
        for (int i = 1; i <= 5; i++) {
            Question question = Question.builder()
                    .course(course)
                    .text("MCQ Question " + i + " for " + course.getName())
                    .correctAnswer(options[RandomGenerator.getDefault().nextInt(4)])
                    .options(Map.of(
                            "A", "Option A",
                            "B", "Option B",
                            "C", "Option C",
                            "D", "Option D"
                    ))
                    .questionType(QuestionType.MCQ)
                    .score(10)
                    .build();
            questionRepository.save(question);
            questionRepository.flush();
        }

        // Create 5 TRUE_FALSE questions
        String[] trueFalseOptions = {"True", "False"};
        for (int i = 1; i <= 5; i++) {
            Question question = Question.builder()
                    .course(course)
                    .text("True/False Question " + i + " for " + course.getName())
                    .options(Map.of(
                            "true", "True",
                            "false", "False"
                    ))
                    .correctAnswer(trueFalseOptions[RandomGenerator.getDefault().nextInt(2)])
                    .questionType(QuestionType.TRUE_FALSE)
                    .score(10)
                    .build();
            questionRepository.save(question);
            questionRepository.flush();
        }
    }

    private void loadAssessmentsForCourse(Course course) {
        // Create 2 assessments of each type
        for (int i = 1; i <= 2; i++) {
            Assessment assignment = Assessment.builder()
                    .course(course)
                    .instructions("Assignment " + i + " for " + course.getName())
                    .type(AssessmentType.ASSIGNMENT)
                    .maxScore(100)
                    .startsAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                    .endsAt(Timestamp.valueOf("2025-12-25 08:00:00"))
                    .build();

            Assessment quiz = Assessment.builder()
                    .course(course)
                    .instructions("Quiz " + i + " for " + course.getName())
                    .type(AssessmentType.QUIZ)
                    .maxScore(50)
                    .startsAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                    .endsAt(Timestamp.valueOf("2025-12-25 08:00:00"))
                    .build();

            assignment = assessmentRepository.save(assignment);
            quiz = assessmentRepository.save(quiz);

            Student student = studentRepository.findByEmail("pop@fcai.student.com").orElseThrow(() -> new RuntimeException("NO STUDENT FOUND"));
            loadAttemptsForAssessmentAndStudent(assignment, student);
            // Assign questions to assessments
            Set<Question> questions = new HashSet<>(questionRepository.findByCourse(course));
            assignment.setQuestions(questions);
            quiz.setQuestions(questions);

            assessmentRepository.save(assignment);
            assessmentRepository.save(quiz);
            assessmentRepository.flush();
            questionRepository.flush();
        }
    }

    private void loadAnnouncementsForCourse(Course course) {
        AbstractUser instructor = course.getInstructor();
        AbstractUser student = studentRepository.findByEmail("sayed@fcai.student.com").orElseThrow(() -> new RuntimeException("NO STUDENT FOUND"));
        for (int i = 1; i <= 5; i++) {
            Announcement announcement = Announcement.builder()
                    .course(course)
                    .postedAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                    .title("Announcement " + i + " for " + course.getName())
                    .content("Announcement " + i + " for the course " + course.getName() + ".")
                    .postedBy(instructor)
                    .build();
            announcement = announcementRepository.save(announcement);
            Comment comment1 = Comment.builder()
                    .content("Comment 1" + " for the announcement " + announcement.getTitle() + " posted by " + instructor.getName())
                    .announcement(announcement)
                    .author(student)
                    .commentedAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                    .build();
            Comment comment2 = Comment.builder()
                    .content("Comment 2" + " for the announcement " + announcement.getTitle() + " posted by " + instructor.getName())
                    .announcement(announcement)
                    .author(student)
                    .commentedAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                    .build();
            announcementRepository.flush();
            commentRepository.saveAndFlush(comment1);
            commentRepository.saveAndFlush(comment2);
        }
    }

    private void loadAttemptsForAssessmentAndStudent(Assessment assessment, Student student) {
        Attempt attempt = Attempt.builder()
                .assessment(assessment)
                .student(student)
                .attemptedAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                .build();
        attemptRepository.saveAndFlush(attempt);
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
}
