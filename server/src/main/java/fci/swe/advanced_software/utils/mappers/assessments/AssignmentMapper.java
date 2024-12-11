package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.dtos.assessments.AssignmentResponseDto;
import fci.swe.advanced_software.models.assessments.Assignment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CourseRepository.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AssignmentMapper {

    @Mapping(target = "course", expression = "java(mapCourse(requestDto.getCourseId(), courseRepository))")
    @Mapping(target = "media", ignore = true)
    Assignment toEntity(AssignmentRequestDto requestDto, CourseRepository courseRepository);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AssignmentResponseDto toResponseDto(Assignment assignment);

    default Course mapCourse(String courseId, CourseRepository courseRepository) {
        if (courseId == null) {
            return null;
        }
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));
    }
}
