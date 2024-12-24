package fci.swe.advanced_software.services.users;

import fci.swe.advanced_software.dtos.Response;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private AuthService authService;

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
}
