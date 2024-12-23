package fci.swe.advanced_software.services.users.admin;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.services.auth.IAuthService;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService implements IAdminService {
    private final IAdminService adminService;
    private final IAuthService authService;
    private final AbstractUserRepository<AbstractUser> userRepository;
    private final RepositoryUtils repositoryUtils;
    private final UserResponseMapper userResponseMapper;

    @Override
    public ResponseEntity<?> deleteUser(String id) {
        AbstractUser user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("User not found!")
                    .build();
        }

        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getUsers(Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<AbstractUser> users = userRepository.findAllUsers(pageable);

        List<UserResponseDto> response = users.stream()
                .map(userResponseMapper::toDto)
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("users", response)
                .build();
    }

    @Override
    public ResponseEntity<?> getUserById(String id) {
        AbstractUser user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("User not found!")
                    .build();
        }

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("user", user)
                .build();
    }

    @Override
    public ResponseEntity<?> updateUser(String id, UserResponseDto registerDto) {
        AbstractUser user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("User not found!")
                    .build();
        }

        user = userResponseMapper.toEntity(registerDto);

        userRepository.save(user);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("User updated successfully!")
                .withData("user", user)
                .build();
    }

}
