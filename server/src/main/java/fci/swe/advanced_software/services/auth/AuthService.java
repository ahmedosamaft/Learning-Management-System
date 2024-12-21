package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.dtos.auth.AuthDto;
import fci.swe.advanced_software.dtos.auth.AuthResponseDto;
import fci.swe.advanced_software.dtos.auth.RegisterDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.dtos.users.UserUpdateDto;
import fci.swe.advanced_software.models.users.*;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.repositories.users.AdminRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.adapters.UserDetailsAdapter;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final AbstractUserRepository<AbstractUser> userRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserResponseMapper userResponseMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> register(RegisterDto registerDto, Boolean isAdmin) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return createErrorResponse("Email is already registered!", HttpStatus.BAD_REQUEST);
        }

        if (!isAdmin && registerDto.getRole() == Role.ADMIN) {
            return createErrorResponse("You are not authorized to create an admin account!", HttpStatus.FORBIDDEN);
        }

        AbstractUser user = createUser(registerDto);
        user = userRepository.save(user);

        String token = jwtService.generateToken(new UserDetailsAdapter(user));

        AuthResponseDto response = buildAuthResponse(user, token);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withData("user", response)
                .withMessage("User registered successfully!")
                .withLocation("/api/v1/auth")
                .build();
    }

    @Override
    public ResponseEntity<?> login(AuthDto authDto) {
        Optional<AbstractUser> user = userRepository.findByEmail(authDto.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(authDto.getPassword(), user.get().getPassword())) {
            return createErrorResponse("Invalid email or password!", HttpStatus.BAD_REQUEST);
        }

        String token = jwtService.generateToken(new UserDetailsAdapter(user.get()));


        AuthResponseDto response = buildAuthResponse(user.get(), token);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("user", response)
                .withMessage("User logged in successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getProfile() {
        AbstractUser user = userRepository.findById(authUtils.getCurrentUserId()).orElse(null);

        UserResponseDto userDto = userResponseMapper.toDto(user);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("user", userDto)
                .withMessage("User profile retrieved successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> updateProfile(UserUpdateDto userUpdateDto) {
        AbstractUser user = userRepository.findById(authUtils.getCurrentUserId()).orElse(null);

        user.setName(userUpdateDto.getName());
        user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        user = userRepository.save(user);
        UserResponseDto userDto = userResponseMapper.toDto(user);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("user", userDto)
                .withMessage("User profile updated successfully!")
                .build();
    }

    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .build();
    }

    private AbstractUser createUser(RegisterDto registerDto) {
        var builder = getBuilder(registerDto.getRole());
        return builder
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(registerDto.getRole())
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

    private AbstractUser.AbstractUserBuilder<?, ?> getBuilder(Role role) {
        return switch (role) {
            case ADMIN -> Admin.builder();
            case STUDENT -> Student.builder();
            case INSTRUCTOR -> Instructor.builder();
        };
    }
}
