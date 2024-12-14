package fci.swe.advanced_software.controllers.auth;

import fci.swe.advanced_software.dtos.auth.AuthDto;
import fci.swe.advanced_software.dtos.auth.RegisterDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.auth.IAuthService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("admin")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> adminRegister(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto, true);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto, false);
    }

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto authDto) {
        return authService.login(authDto);
    }
}
