package ar.edu.unq.desapp.grupoo.criptop2p.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthCredentials Test")
class AuthCredentialsTest {

    @DisplayName("The authentication credentials has email and password")
    @Test
    void testAll() {
        AuthCredentials ac = new AuthCredentials("a@a.a", "1");

        assertEquals("a@a.a", ac.getEmail());
        assertEquals("1", ac.getPassword());
    }

    @DisplayName("The default authentication credentials has email and password too buy nulled")
    @Test
    void testHasDefaultConstructot(){
        var authCred = new AuthCredentials();

        assertNull(authCred.getPassword());
        assertNull(authCred.getEmail());

    }
}
