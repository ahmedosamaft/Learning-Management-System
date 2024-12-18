package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class QuestionMapper {
    protected CourseRepository courseRepository;

    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseDtoToCourse")
    public abstract Question toEntity(QuestionRequestDto requestDto);

    @Mapping(target = "id", source = "question.id")
    @Mapping(target = "courseId", source = "course.id")
    public abstract QuestionResponseDto toResponseDto(Question question);

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
