package fci.swe.advanced_software.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequestDto {

    @NotBlank(message = "ID cannot be blank.")
    @NotNull(message = "ID is required.")
    @UUID(message = "ID must be a valid UUID.")
    private String id;

    @NotBlank(message = "Name cannot be blank.")
    @NotNull(message = "Name is required.")
    @Length(max = 255, message = "Name cannot exceed 255 characters.")
    private String name;

    @NotBlank(message = "Email cannot be blank.")
    @NotNull(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @NotNull(message = "Password is required.")
    @Length(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
}
