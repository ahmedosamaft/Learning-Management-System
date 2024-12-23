package fci.swe.advanced_software.controllers.users.admin;

import fci.swe.advanced_software.dtos.auth.RegisterDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.auth.IAuthService;
import fci.swe.advanced_software.services.users.admin.IAdminService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
@RolesAllowed(Roles.ADMIN)
public class AdminController {
    private final IAdminService adminService;
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> adminRegister(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto, true);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(required = false, defaultValue = "10") Integer size) {
        return adminService.getUsers(page, size);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return adminService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return adminService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserResponseDto userDto) {
        return adminService.updateUser(id, userDto);
    }
}