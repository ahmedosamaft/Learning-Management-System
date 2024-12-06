package fci.swe.advanced_software.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {
    private String email;
    private String password;
}
