package fci.swe.advanced_software.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
}