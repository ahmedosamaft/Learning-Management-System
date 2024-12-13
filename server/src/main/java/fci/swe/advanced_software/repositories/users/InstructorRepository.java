package fci.swe.advanced_software.repositories.users;
import fci.swe.advanced_software.models.users.Instructor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends AbstractUserRepository<Instructor> {
}
