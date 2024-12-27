package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.MediaDto;
import fci.swe.advanced_software.models.courses.Media;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MediaMapper {
    abstract public MediaDto toDto(Media media);
}
