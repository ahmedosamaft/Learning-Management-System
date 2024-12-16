package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class LessonMapper {

    protected final CourseRepository courseRepository;

    public LessonMapper(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseIdToCourse")
    public abstract Lesson toEntity(LessonDto lessonDto);

    @Mapping(target = "courseId", source = "course.id")
    public abstract LessonDto toDto(Lesson lesson);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract void updateEntityFromDto(LessonDto lessonDto, @MappingTarget Lesson lesson);

    @Named("courseIdToCourse")
    public Course courseIdToCourse(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));
    }
}
