package fci.swe.advanced_software.controllers.users;

import fci.swe.advanced_software.services.users.instructor.InstructorService;
import fci.swe.advanced_software.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping
    public ResponseEntity<?> getAllInstructors(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageResult = Pageable.ofSize(size).withPage(page);
        return instructorService.getInstructors(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInstructorById(@PathVariable String id) {
        return instructorService.getInstructor(id);
    }

    @PutMapping
    public String updateInstructor() {
        return "Instructor updated";
    }

    @DeleteMapping("/{id}")
    public String deleteInstructor(@PathVariable String id) {
        return "Instructor with id " + id + " deleted";
    }
}
