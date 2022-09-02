package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;


@DisplayName("Model Tests")
@SpringBootTest
class UserTests {

    @DisplayName("A User can be instantiated")
    @Test
    void userExist(){
        User user = new User("Jim", "Ken", "jk@here.dom", "there 123", "1234", "12345678", "22*1");

        assertNotNull(user);
    }
}

