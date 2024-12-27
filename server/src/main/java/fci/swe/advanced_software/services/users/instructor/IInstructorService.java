package fci.swe.advanced_software.services.users.instructor;

import fci.swe.advanced_software.dtos.Response;
import org.springframework.http.ResponseEntity;

public interface IInstructorService {
    ResponseEntity<Response> getInstructor(String id);

    ResponseEntity<Response> getAllInstructors(Integer page, Integer size);

    ResponseEntity<Response> getCourses(Integer page, Integer size);
}
