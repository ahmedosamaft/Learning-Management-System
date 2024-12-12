package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.dtos.assessments.AssignmentResponseDto;
import fci.swe.advanced_software.models.assessments.Assignment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AssignmentMapper {
    protected CourseRepository courseRepository;

    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseDtoToCourse")
    @Mapping(target = "media", ignore = true)
    public abstract Assignment toEntity(AssignmentRequestDto requestDto);


    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract AssignmentResponseDto toResponseDto(Assignment assignment);

    @Named("courseDtoToCourse")
    public Course courseDtoToCourse(String courseId) {
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
