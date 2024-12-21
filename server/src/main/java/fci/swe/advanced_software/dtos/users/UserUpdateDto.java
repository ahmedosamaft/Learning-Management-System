package fci.swe.advanced_software.dtos.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {

    @NotBlank(message = "Name cannot be blank.")
    @NotNull(message = "Name is required.")
    @Length(max = 255, message = "Name cannot exceed 255 characters.")
    private String name;

    @NotBlank(message = "Password cannot be blank.")
    @NotNull(message = "Password is required.")
    @Length(max = 255, message = "Password cannot exceed 255 characters.")
    private String password;
}
