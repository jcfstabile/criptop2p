package ar.edu.unq.desapp.grupoo.criptop2p.security;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("UserDetailsServiceImpl Test")
@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

    @DisplayName("UserDetailsImpl Test")
    @Test
    void testAllDetails() {
        User user = mock(User.class);
        when(user.getPassword()).thenReturn("1234");
        when(user.getEmail()).thenReturn("a@a.a");
        when(user.getName()).thenReturn("a");

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertEquals("1234", userDetails.getPassword());
        assertEquals("a", userDetails.getName());
        assertEquals("a@a.a", userDetails.getUsername());
        assertEquals(Collections.emptyList(), userDetails.getAuthorities());
    }
}