package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentQuestionsDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AssessmentQuestionsMapper {
    protected CourseRepository courseRepository;

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "questions", source = "questions")
    public abstract AssessmentQuestionsDto toResponseDto(Assessment assessment);

    @Autowired
    protected void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

}
