package fci.swe.advanced_software.services.courses.lesson;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.services.INotificationsService;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.courses.LessonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private INotificationsService notificationsService;

    @Mock
    private RepositoryUtils repositoryUtils;

    private Course buildMockCourse() {
        return Course.builder()
                .id("CS101")
                .code("CS101")
                .name("Introduction to Computer Science")
                .build();
    }

    private Instructor buildMockInstructor() {
        return Instructor.builder()
                .id("INS001")
                .name("John Doe")
                .build();
    }

    private LessonDto buildMockLessonDto() {
        return LessonDto.builder()
                .id("LESSON1")
                .title("Introduction to Java")
                .content("This is a basic lesson on Java programming.")
                .courseId("CS101")
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void LessonService_GetAllLessons_ReturnsLessons() {
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(Lesson.builder().id("LESSON1").title("Introduction to Java").build());
        PageImpl<Lesson> lessonPage = new PageImpl<>(lessonList);

        when(repositoryUtils.getPageable(anyInt(), anyInt(), any(), any())).thenReturn(PageRequest.of(0, 10));
        when(lessonRepository.findAllByCourseId("CS101", PageRequest.of(0, 10))).thenReturn(lessonPage);
        when(lessonMapper.toDto(any(Lesson.class))).thenReturn(buildMockLessonDto());

        ResponseEntity<?> response = lessonService.getAllLessons("CS101", 0, 10);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void LessonService_GetLessonById_ReturnsLesson() {
        when(lessonRepository.findById("LESSON1")).thenReturn(Optional.of(Lesson.builder().id("LESSON1").title("Introduction to Java").build()));
        when(lessonMapper.toDto(any(Lesson.class))).thenReturn(buildMockLessonDto());

        ResponseEntity<?> response = lessonService.getLessonById("LESSON1");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void LessonService_GetLessonById_ReturnsNotFound() {
        when(lessonRepository.findById("LESSON999")).thenReturn(Optional.empty());

        ResponseEntity<?> response = lessonService.getLessonById("LESSON999");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void LessonService_CreateLesson_ReturnsCreatedLesson() {
        LessonDto lessonDto = buildMockLessonDto();
        Lesson lesson = Lesson.builder().id("LESSON1").title("Introduction to Java").build();

        when(lessonMapper.toEntity(lessonDto)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(lessonDto);

        ResponseEntity<?> response = lessonService.createLesson(lessonDto, "CS101");
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void LessonService_UpdateLesson_ReturnsUpdatedLesson() {
        LessonDto lessonDto = buildMockLessonDto();
        Lesson lesson = Lesson.builder().id("LESSON1").title("Introduction to Java").build();

        when(lessonRepository.findById("LESSON1")).thenReturn(Optional.of(lesson));
        when(lessonMapper.toEntity(lessonDto)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(lessonDto);

        ResponseEntity<?> response = lessonService.updateLesson("LESSON1", lessonDto);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void LessonService_UpdateLesson_ReturnsNotFound() {
        when(lessonRepository.findById("LESSON999")).thenReturn(Optional.empty());

        ResponseEntity<?> response = lessonService.updateLesson("LESSON999", buildMockLessonDto());
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void LessonService_DeleteLesson_DeletesLesson() {
        when(lessonRepository.existsById("LESSON1")).thenReturn(true);

        doNothing().when(lessonRepository).deleteById("LESSON1");

        ResponseEntity<?> response = lessonService.deleteLesson("LESSON1");
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void LessonService_DeleteLesson_ReturnsNotFound() {
        when(lessonRepository.existsById("LESSON999")).thenReturn(false);

        doNothing().when(lessonRepository).deleteById("LESSON999");

        ResponseEntity<?> response = lessonService.deleteLesson("LESSON999");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

}
