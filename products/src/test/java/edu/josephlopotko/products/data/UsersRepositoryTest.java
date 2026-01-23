package edu.josephlopotko.products.data;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.josephlopotko.products.models.UserEntity;

@SpringBootTest
@Transactional
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByUsername_ReturnsUser() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        usersRepository.save(user);

        // Act
        UserEntity found = usersRepository.findByUsername("testuser");

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
        assertThat(found.getRole()).isEqualTo("ROLE_USER");
        assertThat(found.isEnabled()).isTrue();
    }

    @Test
    public void testFindByUsername_ReturnsNull_WhenUserDoesNotExist() {
        // Act
        UserEntity found = usersRepository.findByUsername("nonexistent");

        // Assert
        assertThat(found).isNull();
    }

    @Test
    public void testSaveUser_PersistsUser() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUsername("newuser");
        user.setPassword("encodedpassword");
        user.setRole("ROLE_USER");
        user.setEnabled(true);

        // Act
        UserEntity savedUser = usersRepository.save(user);

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
        
        // Verify persistence
        UserEntity found = usersRepository.findByUsername("newuser");
        assertThat(found).isNotNull();
        assertThat(found.getPassword()).isEqualTo("encodedpassword");
    }
}
