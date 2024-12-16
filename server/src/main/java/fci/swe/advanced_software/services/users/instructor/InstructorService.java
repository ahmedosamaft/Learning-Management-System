package fci.swe.advanced_software.services.users.instructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InstructorService {
    ResponseEntity<?> getInstructor(String id);
    ResponseEntity<?> getInstructors(Pageable pageable);
}
