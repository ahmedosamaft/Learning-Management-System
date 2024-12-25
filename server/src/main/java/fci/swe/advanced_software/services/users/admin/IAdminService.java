package fci.swe.advanced_software.services.users.admin;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.users.UserUpdateDto;
import org.springframework.http.ResponseEntity;

public interface IAdminService {
    ResponseEntity<Response> deleteUser(String id);

    ResponseEntity<Response> getUsers(Integer page, Integer size);

    ResponseEntity<Response> getUserById(String id);

    ResponseEntity<Response> updateUser(String id, UserUpdateDto registerDto);
}
