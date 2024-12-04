package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbstractUserRepository <T extends AbstractUser>extends JpaRepository<T,String> {
    Optional<T> findByEmail(String email);
}
