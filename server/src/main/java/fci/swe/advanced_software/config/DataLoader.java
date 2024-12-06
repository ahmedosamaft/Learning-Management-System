package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.auth.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader {
    private RoleRepository roleRepository;

    @PostConstruct
    public void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder().name("STUDENT").build());
            roleRepository.save(Role.builder().name("INSTRUCTOR").build());
            roleRepository.save(Role.builder().name("ADMIN").build());
        }
    }
}