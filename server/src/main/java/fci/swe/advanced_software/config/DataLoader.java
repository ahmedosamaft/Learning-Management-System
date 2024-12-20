package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Admin;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.repositories.users.AdminRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.random.RandomGenerator;

@Component
@AllArgsConstructor
public class DataLoader {
    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;
    private InstructorRepository instructorRepository;
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private AssessmentRepository assessmentRepository;
    private QuestionRepository questionRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
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

            loadQuestionsForCourse(softwareEngineering);
            loadQuestionsForCourse(oop);

            loadAssessmentsForCourse(softwareEngineering);
            loadAssessmentsForCourse(oop);
        }
    }

    private void loadLessonsToCourse(Course course) {
        for (int i = 1; i <= 5; i++) {
            lessonRepository.save(Lesson.builder()
                    .title("Lesson " + i + " for " + course.getName())
                    .content("This is the content of Lesson " + i + " for the course " + course.getName() + ".")
                    .otp(course.getCode().toLowerCase() + i)
                    .course(course)
                    .build());
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
        }

        // Create 5 TRUE_FALSE questions
        String[] trueFalseOptions = {"True", "False"};
        for (int i = 1; i <= 5; i++) {
            Question question = Question.builder()
                    .course(course)
                    .text("True/False Question " + i + " for " + course.getName())
                    .options(Map.of(
                            "True", "True",
                            "False", "False"
                    ))
                    .correctAnswer(trueFalseOptions[RandomGenerator.getDefault().nextInt(2)])
                    .questionType(QuestionType.TRUE_FALSE)
                    .score(10)
                    .build();
            questionRepository.save(question);
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
                    .endsAt(Timestamp.valueOf("2024-12-25 08:00:00"))
                    .build();

            Assessment quiz = Assessment.builder()
                    .course(course)
                    .instructions("Quiz " + i + " for " + course.getName())
                    .type(AssessmentType.QUIZ)
                    .maxScore(50)
                    .startsAt(Timestamp.valueOf("2024-12-20 08:00:00"))
                    .endsAt(Timestamp.valueOf("2024-12-22 08:00:00"))
                    .build();

            assignment = assessmentRepository.save(assignment);
            quiz = assessmentRepository.save(quiz);

            // Assign questions to assessments
            Set<Question> questions = new HashSet<>(questionRepository.findByCourse(course));
            assignment.setQuestions(questions);
            quiz.setQuestions(questions);

            assessmentRepository.save(assignment);
            assessmentRepository.save(quiz);
        }
    }
}
