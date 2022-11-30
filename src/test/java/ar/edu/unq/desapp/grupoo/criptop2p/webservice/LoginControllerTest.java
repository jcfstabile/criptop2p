package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.controllers.LoginController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoginController Tests")
@SpringBootTest
class LoginControllerTest {
    @Autowired
    LoginController loginController;

    @DisplayName("Ping is undertanded by login controller")
    @Test
    void testLoginControllerUnderstandsPing() throws NoSuchMethodException {
        assertNotNull(loginController.getClass().getMethod("ping"));
    }

    @DisplayName("Login is undertanded by login controller")
    @Test
    void testLoginControllerUnderstandsLogin() throws NoSuchMethodException {
        assertNotNull(loginController.getClass().getMethod("login", UserLoginDTO.class));
    }

    @DisplayName("The login's response is the expected")
    @Test
    void testLoginHasResponseThrowAnError(){
        UserLoginDTO fakeUserLogin = new UserLoginDTO("fakeuser@fake.fake", "FakePass1");
        Exception exception = assertThrows(BadCredentialsException.class, () ->
                loginController.login(fakeUserLogin));
        assertEquals("Bad credentials", exception.getMessage());
    }
}
