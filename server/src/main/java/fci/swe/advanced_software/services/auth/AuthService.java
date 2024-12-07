package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.dtos.auth.AuthDto;
import fci.swe.advanced_software.dtos.auth.AuthResponseDto;
import fci.swe.advanced_software.dtos.auth.RegisterDto;
import fci.swe.advanced_software.models.users.*;
import fci.swe.advanced_software.repositories.auth.RoleRepository;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.adapters.UserDetailsAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final RoleRepository roleRepository;
    private final AbstractUserRepository<AbstractUser> userRepository;
    private final AbstractUserRepository<Admin> adminRepository;
    private final AbstractUserRepository<Student> studentRepository;
    private final AbstractUserRepository<Instructor> instructorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseEntity<?> register(RegisterDto registerDto, Boolean isAdmin) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return createErrorResponse("Email is already registered!");
        }

        if (!isValidRole(registerDto.getRole())) {
            return createErrorResponse("Role is invalid!");
        }

        if (!isAdmin && registerDto.getRole().equals(Roles.ADMIN)) {
            return createErrorResponse("You are not authorized to create an admin account!");
        }

        Optional<Role> role = roleRepository.findByName(registerDto.getRole());
        if (role.isEmpty()) {
            return createErrorResponse("Role not found!");
        }

        AbstractUser user = createUser(registerDto, role.get());
        user = userRepository.save(user);

        String token = jwtService.generateToken(new UserDetailsAdapter(user));

        AuthResponseDto response = buildAuthResponse(user, token);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withData(response)
                .withMessage("User registered successfully!")
                .withLocation("/api/v1/auth")
                .build();
    }

    public ResponseEntity<?> login(AuthDto authDto) {
        Optional<AbstractUser> user = userRepository.findByEmail(authDto.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(authDto.getPassword(), user.get().getPassword())) {
            return createErrorResponse("Invalid email or password!");
        }

        String token = jwtService.generateToken(new UserDetailsAdapter(user.get()));


        AuthResponseDto response = buildAuthResponse(user.get(), token);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(response)
                .withMessage("User logged in successfully!")
                .build();
    }


    private boolean isValidRole(String role) {
        return role.equals(Roles.INSTRUCTOR) || role.equals(Roles.STUDENT) || role.equals(Roles.ADMIN);
    }

    private ResponseEntity<?> createErrorResponse(String message) {
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withMessage(message)
                .build();
    }

    private AbstractUser createUser(RegisterDto registerDto, Role role) {
        var builder = getBuilder(role.getName());
        return builder
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Set.of(role))
                .build();
    }

    private AuthResponseDto buildAuthResponse(AbstractUser user, String token) {
        return AuthResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    private AbstractUser.AbstractUserBuilder<?, ?> getBuilder(String role) {
        return switch (role) {
            case Roles.ADMIN -> Admin.builder();
            case Roles.STUDENT -> Student.builder();
            case Roles.INSTRUCTOR -> Instructor.builder();
            default -> null;
        };
    }
}
