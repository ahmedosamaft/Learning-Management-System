package fci.swe.advanced_software.dtos.auth;

import fci.swe.advanced_software.models.users.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class RegisterDto {
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 6, max = 255)
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = Roles.ADMIN + "|" + Roles.INSTRUCTOR + "|" + Roles.STUDENT)
    private String role;
}