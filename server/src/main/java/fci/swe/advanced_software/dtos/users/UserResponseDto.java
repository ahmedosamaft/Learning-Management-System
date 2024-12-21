package fci.swe.advanced_software.dtos.users;

import fci.swe.advanced_software.models.users.Role;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String id;

    private String name;

    private String email;

    private Role role;
}
