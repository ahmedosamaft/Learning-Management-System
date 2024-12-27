package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByCourse() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");

        Question question1 = createQuestion(course, "What is Java?", QuestionType.MCQ, 5);
        Question question2 = createQuestion(course, "True or False: Java is an OOP language.", QuestionType.TRUE_FALSE, 2);

        questionRepository.save(question1);
        questionRepository.save(question2);

        var questions = questionRepository.findByCourse(course);

        assertNotNull(questions);
        assertEquals(2, questions.size());
        assertTrue(questions.stream().anyMatch(q -> q.getText().equals("What is Java?")));
        assertTrue(questions.stream().anyMatch(q -> q.getText().equals("True or False: Java is an OOP language.")));
    }

    @Test
    void testFindAllByCourseId() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS102", "Data Structures");

        Question question1 = createQuestion(course, "What is a linked list?", QuestionType.SHORT_ANSWER, 3);
        Question question2 = createQuestion(course, "What is the time complexity of binary search?", QuestionType.MCQ, 4);

        questionRepository.save(question1);
        questionRepository.save(question2);

        var questions = questionRepository.findByCourse(course);

        assertNotNull(questions);
        assertEquals(2, questions.size());
        assertTrue(questions.stream().anyMatch(q -> q.getText().equals("What is a linked list?")));
        assertTrue(questions.stream().anyMatch(q -> q.getText().equals("What is the time complexity of binary search?")));
    }

    private Instructor createInstructor() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        return instructor;
    }

    private Course createCourse(Instructor instructor, String code, String name) {
        entityManager.persist(instructor);

        Course course = new Course();
        course.setCode(code);
        course.setName(name);
        course.setDescription("Description for " + name);
        course.setInstructor(instructor);
        entityManager.persist(course);

        return course;
    }

    private Question createQuestion(Course course, String text, QuestionType type, int score) {
        Question question = new Question();
        question.setCourse(course);
        question.setText(text);
        question.setQuestionType(type);
        question.setScore(score);
        question.setCorrectAnswer("Correct");
        question.setOptions(new HashMap<>());
        return question;
    }
}
