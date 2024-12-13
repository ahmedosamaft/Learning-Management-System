package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    @NotBlank(message = "Course code is required.")
    @Size(min = 3, max = 10, message = "Course code must be between 3 and 10 characters.")
    private String code;

    @NotBlank(message = "Course ID is required.")
    private String id;

    @NotBlank(message = "Course name is required.")
    @Size(max = 100, message = "Course name must be less than or equal to 100 characters.")
    private String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters.")
    private String description;

    @NotBlank(message = "Instructor ID is required.")
    private String instructorId;

    @NotBlank(message = "Instructor name is required.")
    @Size(max = 100, message = "Instructor name must be less than or equal to 100 characters.")
    private String instructorName;
}
