package fci.swe.advanced_software.repositories.users;
import fci.swe.advanced_software.models.users.Student;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends AbstractUserRepository<Student> {
    Optional<Student> findByEmail(String email);
}
