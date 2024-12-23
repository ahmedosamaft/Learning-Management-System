package fci.swe.advanced_software.dtos.users;

import fci.swe.advanced_software.models.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdDto {
    private String id;

    private String name;

    private String email;

    private Role role;
}
