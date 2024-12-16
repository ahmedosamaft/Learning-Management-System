package fci.swe.advanced_software.services.users.instructor;

import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    @Override
    public ResponseEntity<?> getInstructor(String id) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        if (instructor == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Instructor not found!")
                    .build();
        }


        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Instructor found!")
                .withData(instructor)
                .build();
    }

    @Override
    public ResponseEntity<?> getInstructors(Pageable pageable) {
        Page<Instructor> instructorsPage = instructorRepository.findAll(pageable);
//        List<InstructorDto> instructors = instructorsPage.stream()
        return ResponseEntityBuilder.create()
                .withMessage("Instructors found")
                .withData(instructorsPage.getContent())
                .build();
    }
}
