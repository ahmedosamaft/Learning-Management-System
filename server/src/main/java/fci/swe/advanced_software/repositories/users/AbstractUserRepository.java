package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractUserRepository<T extends AbstractUser> extends AbstractEntityRepository<T> {
}
