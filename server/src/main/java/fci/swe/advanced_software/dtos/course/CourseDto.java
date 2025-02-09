package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {

    @NotBlank(message = "Course code is required.")
    @Size(min = 3, max = 10, message = "Course code must be between 3 and 10 characters.")
    private String code;

    private String id;

    @NotBlank(message = "Course name is required.")
    @Size(max = 100, message = "Course name must be less than or equal to 100 characters.")
    private String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters.")
    private String description;

    private String instructorId;

    private String instructorName;
}
