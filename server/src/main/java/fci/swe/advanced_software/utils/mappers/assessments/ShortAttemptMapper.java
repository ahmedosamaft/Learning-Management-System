package fci.swe.advanced_software.utils.mappers.assessments;

import fci.swe.advanced_software.dtos.assessments.Attempt.AttemptShortDto;
import fci.swe.advanced_software.models.assessments.Attempt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ShortAttemptMapper {

    @Mapping(target = "answers", ignore = true)
    public abstract Attempt toEntity(AttemptShortDto attemptShortDto);
}
