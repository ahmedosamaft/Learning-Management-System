package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.dtos.auth.AuthDto;
import fci.swe.advanced_software.dtos.auth.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> register(RegisterDto registerDto, Boolean isAdmin);
    ResponseEntity<?> login(AuthDto authDto);
}
