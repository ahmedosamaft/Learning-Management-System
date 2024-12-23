package fci.swe.advanced_software.services.users.admin;

import fci.swe.advanced_software.dtos.users.UserUpdateDto;
import org.springframework.http.ResponseEntity;

public interface IAdminService {
    ResponseEntity<?> deleteUser(String id);

    ResponseEntity<?> getUsers(Integer page, Integer size);

    ResponseEntity<?> getUserById(String id);

    ResponseEntity<?> updateUser(String id, UserUpdateDto registerDto);
}
