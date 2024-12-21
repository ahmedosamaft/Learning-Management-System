package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.announcement.CommentDto;
import fci.swe.advanced_software.models.courses.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CommentMapper {
    public abstract Comment toEntity(CommentDto requestDto);
    public abstract CommentDto toResponseDto(Comment comment);
}
