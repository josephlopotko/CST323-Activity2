package edu.josephlopotko.products.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.josephlopotko.products.data.UsersRepository;
import edu.josephlopotko.products.models.UserEntity;

@SpringBootTest
@Transactional
public class AuthControllerTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegistration_PersistsUser() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        
        // Act
        usersRepository.save(user);

        // Assert
        UserEntity found = usersRepository.findByUsername("testuser");
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
        assertThat(found.getRole()).isEqualTo("ROLE_USER");
        assertThat(found.isEnabled()).isTrue();
    }

    @Test
    public void testRegistration_EncodesPassword() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Assert
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}
