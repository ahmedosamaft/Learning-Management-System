package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.CourseSearchDto;
import fci.swe.advanced_software.models.courses.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CourseToElasticsearchMapper {
    public abstract CourseSearchDto toES(Course course);
}
