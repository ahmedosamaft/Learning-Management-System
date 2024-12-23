package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbstractUserRepository<T extends AbstractUser> extends AbstractEntityRepository<T> {
    Optional<T> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<AbstractUser> findAllUsers(Pageable pageable);
}
