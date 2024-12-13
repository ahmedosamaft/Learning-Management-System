package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CourseMapper {

    protected CourseRepository courseRepository;
    protected InstructorRepository instructorRepository;

    // Convert CourseDto to Course entity
    @Mapping(target = "instructor", source = "instructorId", qualifiedByName = "instructorDtoToInstructor")
    @Mapping(target = "lessons", ignore = true)  // Ignore lessons and other relationships that are handled elsewhere
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "quizzes", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    public abstract Course toEntity(CourseDto courseDto);

    // Convert Course entity to CourseDto
    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(target = "instructorName", source = "instructor.name")
    public abstract CourseDto toDto(Course course);

    // Update existing Course entity from CourseDto
    @Mapping(target = "instructor", ignore = true) // Instructor will be set separately
    @Mapping(target = "id", ignore = true)         // Prevent changing the ID
    @Mapping(target = "version", ignore = true)    // Prevent changing the version field
    public abstract void updateEntityFromDto(CourseDto courseDto, @MappingTarget Course course);

    // Convert instructorId to Instructor entity using InstructorRepository
    @Named("instructorDtoToInstructor")
    public Instructor instructorDtoToInstructor(String instructorId) {
        if (instructorId == null) {
            return null;
        }
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid instructor ID: " + instructorId));
    }

    @Autowired
    protected void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    protected void setInstructorRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }
}
