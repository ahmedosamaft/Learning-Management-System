package fci.swe.advanced_software.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {
    private String id;
    private String name;
    private String email;
    private String password;
}
