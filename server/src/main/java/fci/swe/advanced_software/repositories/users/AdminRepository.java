package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends AbstractUserRepository<Admin> {
}
