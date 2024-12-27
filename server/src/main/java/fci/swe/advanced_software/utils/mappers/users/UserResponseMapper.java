package fci.swe.advanced_software.utils.mappers.users;

import fci.swe.advanced_software.dtos.users.UserIdDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.users.AbstractUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserResponseMapper {

    @Mapping(target = "password", ignore = true)
    public abstract AbstractUser toEntity(UserResponseDto user);

    public abstract UserIdDto toUserIdDto(AbstractUser user);

    public abstract UserResponseDto toDto(AbstractUser user);
}
