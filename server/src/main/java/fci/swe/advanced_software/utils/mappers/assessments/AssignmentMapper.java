package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.dtos.assessments.AssignmentResponseDto;
import fci.swe.advanced_software.models.assessments.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "media", ignore = true)
    Assignment toEntity(AssignmentRequestDto requestDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AssignmentResponseDto toResponseDto(Assignment assignment);
}
