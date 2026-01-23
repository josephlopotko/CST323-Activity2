package edu.josephlopotko.products.data;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoder_IsBCrypt() {
        assertThat(passwordEncoder).isNotNull();
        assertThat(passwordEncoder.getClass().getSimpleName()).isEqualTo("BCryptPasswordEncoder");
    }

    @Test
    public void testPasswordEncoder_EncodesCorrectly() {
        String rawPassword = "testpassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }

    @Test
    public void testPasswordEncoder_DifferentEncodingsForSamePassword() {
        String rawPassword = "samepassword";
        String encoded1 = passwordEncoder.encode(rawPassword);
        String encoded2 = passwordEncoder.encode(rawPassword);

        // BCrypt should produce different hashes each time
        assertThat(encoded1).isNotEqualTo(encoded2);
        // But both should match the original
        assertThat(passwordEncoder.matches(rawPassword, encoded1)).isTrue();
        assertThat(passwordEncoder.matches(rawPassword, encoded2)).isTrue();
    }
}
