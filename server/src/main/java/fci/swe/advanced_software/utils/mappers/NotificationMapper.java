package fci.swe.advanced_software.utils.mappers;

import fci.swe.advanced_software.dtos.NotificationDto;
import fci.swe.advanced_software.models.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class NotificationMapper {
    @Mapping(target = "courseId", source = "course.id")
    public abstract NotificationDto toDto(Notification notification);
}
