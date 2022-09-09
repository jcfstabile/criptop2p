package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DisplayName("Utils Tests")
@SpringBootTest
class UserFactoryTests {

    @DisplayName("A User can be instantiated")
    @Test
    void userFactoryReturnAUserWhenTheParametersAreRight(){
        User user = new UserFactory().createUser("Jim", "Ken", "jk@here.dom", "there 123", "Pepito12", "12345678", "1111111111111111111111");
        assertNotNull(user);
        assertEquals("Jim", user.getName());
        assertEquals("Ken", user.getSurname());
        assertEquals("jk@here.dom", user.getEmail());
        assertEquals("there 123", user.getAddress());
        assertEquals( "Pepito12", user.getPassword());
        assertEquals("12345678", user.getWalletAddress());
        assertEquals("1111111111111111111111", user.getCvu());
    }
}

