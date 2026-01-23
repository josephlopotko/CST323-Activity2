package edu.josephlopotko.products.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.josephlopotko.products.data.UsersRepository;
import edu.josephlopotko.products.models.UserEntity;

@SpringBootTest
@Transactional
public class UserAdminControllerTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        // Create test users
        UserEntity adminUser = new UserEntity();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRole("ROLE_ADMIN");
        adminUser.setEnabled(true);
        usersRepository.save(adminUser);

        UserEntity regularUser = new UserEntity();
        regularUser.setUsername("user");
        regularUser.setPassword(passwordEncoder.encode("user123"));
        regularUser.setRole("ROLE_USER");
        regularUser.setEnabled(true);
        usersRepository.save(regularUser);
    }

    @Test
    public void testAdminUserExists() {
        UserEntity admin = usersRepository.findByUsername("admin");
        assertThat(admin).isNotNull();
        assertThat(admin.getRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    public void testRegularUserExists() {
        UserEntity user = usersRepository.findByUsername("user");
        assertThat(user).isNotNull();
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
    }
}
