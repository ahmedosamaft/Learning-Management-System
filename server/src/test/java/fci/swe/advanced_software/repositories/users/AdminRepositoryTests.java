package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.Admin;
import fci.swe.advanced_software.models.users.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void AdminRepository_Save_ReturnsSavedAdmin() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        admin = adminRepository.save(admin);

        assertThat(admin).isNotNull();
        assertThat(admin.getId()).isNotNull();
    }

    @Test
    public void AdminRepository_FindById_ReturnsAdmin() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        admin = adminRepository.save(admin);

        Admin foundAdmin = adminRepository.findById(admin.getId()).orElse(null);

        assertThat(foundAdmin).isNotNull();
        assertThat(foundAdmin.getId()).isEqualTo(admin.getId());
    }

    @Test
    public void AdminRepository_FindById_ReturnsNull() {
        Admin foundAdmin = adminRepository.findById(String.valueOf(UUID.randomUUID())).orElse(null);

        assertThat(foundAdmin).isNull();
    }

    @Test
    public void AdminRepository_FindAll_ReturnsAdmins() {
        Admin admin1 = Admin.builder()
                .name("testA")
                .email("testA@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        Admin admin2 = Admin.builder()
                .name("testB")
                .email("testB@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        adminRepository.save(admin1);
        adminRepository.save(admin2);

        Iterable<Admin> admins = adminRepository.findAll();

        assertThat(admins).hasSize(2);
        assertThat(admins).contains(admin1, admin2);
    }

    @Test
    public void AdminRepository_FindByEmail_ReturnsAdmin() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        admin = adminRepository.save(admin);

        Admin foundAdmin = adminRepository.findByEmail(admin.getEmail()).orElse(null);

        assertThat(foundAdmin).isNotNull();
        assertThat(foundAdmin.getId()).isEqualTo(admin.getId());
    }

    @Test
    public void AdminRepository_FindByEmail_ReturnsNull() {
        Admin foundAdmin = adminRepository.findByEmail("test@fci.swe.com").orElse(null);

        assertThat(foundAdmin).isNull();
    }

    @Test
    public void AdminRepository_ExistsById_ReturnsTrue() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        adminRepository.save(admin);

        assertThat(adminRepository.existsById(admin.getId())).isTrue();
    }

    @Test
    public void AdminRepository_ExistsById_ReturnsFalse() {
        assertThat(adminRepository.existsById(String.valueOf(UUID.randomUUID()))).isFalse();
    }

    @Test
    public void AdminRepository_ExistsByEmail_ReturnsTrue() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        adminRepository.save(admin);

        assertThat(adminRepository.existsByEmail(admin.getEmail())).isTrue();
    }

    @Test
    public void AdminRepository_ExistsByEmail_ReturnsFalse() {
        assertThat(adminRepository.existsByEmail("test@fci.swe.com")).isFalse();
    }

    @Test
    public void AdminRepository_Delete_DeletesAdmin() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        admin = adminRepository.save(admin);

        adminRepository.delete(admin);

        Admin foundAdmin = adminRepository.findById(admin.getId()).orElse(null);

        assertThat(foundAdmin).isNull();
    }

    @Test
    public void AdminRepository_DeleteById_DeletesAdmin() {
        Admin admin = Admin.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();

        admin = adminRepository.save(admin);

        adminRepository.deleteById(admin.getId());

        Admin foundAdmin = adminRepository.findById(admin.getId()).orElse(null);

        assertThat(foundAdmin).isNull();
    }
}
