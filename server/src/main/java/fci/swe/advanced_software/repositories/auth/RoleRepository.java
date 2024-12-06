package fci.swe.advanced_software.repositories.auth;

import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends AbstractEntityRepository<Role> {
    Optional<Role> findByName(String name);
}
