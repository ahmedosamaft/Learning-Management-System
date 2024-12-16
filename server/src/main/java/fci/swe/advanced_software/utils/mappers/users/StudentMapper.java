package fci.swe.advanced_software.utils.mappers.users;

import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import fci.swe.advanced_software.models.users.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class StudentMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "attempts", ignore = true)
    public abstract Student toEntity(StudentRequestDto requestDto);


    @Mapping(target = "password", ignore = true)
    public abstract StudentRequestDto toDto(Student student);
}
