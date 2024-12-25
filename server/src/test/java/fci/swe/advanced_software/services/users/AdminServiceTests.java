package fci.swe.advanced_software.services.users;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.users.UserIdDto;
import fci.swe.advanced_software.dtos.users.UserUpdateDto;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.services.auth.AuthService;
import fci.swe.advanced_software.services.users.admin.AdminService;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AbstractUserRepository<AbstractUser> userRepository;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private AuthService authServiceMock;

    @Test
    public void deleteUser_WhenUserExists_ShouldDeleteSuccessfully() {
        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<Response> response = adminService.deleteUser(user.getId());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteUser_WhenUserNotExists_ShouldReturnNotFound() {
        String id = UUID.randomUUID().toString();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = adminService.deleteUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).delete(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found!", response.getBody().getMessage());
    }

    @Test
    public void getUsers_ShouldReturnUsers() {
        int page = 1;
        int size = 10;

        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        UserIdDto userIdDto = UserIdDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

        Page<AbstractUser> users = new PageImpl<>(List.of(user), pageable, 1);

        when(repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt")).thenReturn(pageable);
        when(userRepository.findAll(pageable)).thenReturn(users);
        when(userResponseMapper.toUserIdDto(user)).thenReturn(userIdDto);

        ResponseEntity<Response> response = adminService.getUsers(page, size);

        verify(repositoryUtils, times(1)).getPageable(page, size, Sort.Direction.ASC, "createdAt");
        verify(userRepository, times(1)).findAll(pageable);
        verify(userResponseMapper, times(1)).toUserIdDto(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Users retrieved successfully!", response.getBody().getMessage());
    }

    @Test
    public void getUserById_WhenUserExists_ShouldReturnUser() {
        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        UserIdDto userIdDto = UserIdDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userResponseMapper.toUserIdDto(user)).thenReturn(userIdDto);

        ResponseEntity<Response> response = adminService.getUserById(user.getId());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userResponseMapper, times(1)).toUserIdDto(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User retrieved successfully!", response.getBody().getMessage());
    }

    @Test
    public void getUserById_WhenUserNotExists_ShouldReturnNotFound() {
        String id = UUID.randomUUID().toString();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = adminService.getUserById(id);

        verify(userRepository, times(1)).findById(id);
        verify(userResponseMapper, never()).toUserIdDto(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found!", response.getBody().getMessage());
    }

    @Test
    public void updateUser_ShouldUpdateUser() {
        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        UserIdDto userIdDto = UserIdDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .name("new name")
                .password("12345678")
                .build();

        when(authServiceMock.updateProfile(userUpdateDto, user.getId())).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Response> response = adminService.updateUser(user.getId(), userUpdateDto);

        verify(authServiceMock, times(1)).updateProfile(userUpdateDto, user.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
