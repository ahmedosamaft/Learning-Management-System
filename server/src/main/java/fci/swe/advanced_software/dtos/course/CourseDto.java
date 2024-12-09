package fci.swe.advanced_software.dtos.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private String code;
    @Getter
    private String id;
    private String name;
    private String description;
    private String instructorId;
    @Getter
    private String instructorName;

}
