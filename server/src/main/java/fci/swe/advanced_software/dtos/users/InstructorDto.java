package fci.swe.advanced_software.dtos.users;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
public class InstructorDto {
    @UUID
    private String id;

    @Length(min = 3, max = 255)
    private String name;

    @Email
    private String email;

    @Length(min = 6, max = 255)
    private String password;
}
