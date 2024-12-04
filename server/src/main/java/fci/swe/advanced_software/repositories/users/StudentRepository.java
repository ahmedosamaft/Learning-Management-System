package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends AbstractUserRepository<Student> {
}
