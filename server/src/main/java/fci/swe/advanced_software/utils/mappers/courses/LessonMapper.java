package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.LessonDto;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class LessonMapper {

    protected CourseRepository courseRepository;

    // Convert LessonDto to Lesson entity
    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseIdToCourse")
    @Mapping(target = "media", ignore = true)  // Ignore media or handle it separately
    public abstract Lesson toEntity(LessonDto lessonDto);

    // Convert Lesson entity to LessonDto
    @Mapping(target = "id", source = "id") // Map the id from Lesson to LessonDto
    @Mapping(target = "courseId", source = "course.id")
    public abstract LessonDto toDto(Lesson lesson);

    // Update existing Lesson entity from LessonDto
    @Mapping(target = "course", ignore = true) // Course will be set separately
    @Mapping(target = "id", ignore = true)      // Prevent changing the ID
    @Mapping(target = "version", ignore = true) // Prevent changing the version field
    public abstract void updateEntityFromDto(LessonDto lessonDto, @MappingTarget Lesson lesson);

    // Convert courseId to Course entity using CourseRepository
    @Named("courseIdToCourse")
    public Course courseIdToCourse(String courseId) {
        if (courseId == null) {
            return null;
        }
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));
    }

    @Autowired
    protected void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}