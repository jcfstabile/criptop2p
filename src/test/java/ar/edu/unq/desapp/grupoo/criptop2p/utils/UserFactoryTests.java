package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.NullParameterException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DisplayName("Utils Tests")
@SpringBootTest
class UserFactoryTests {

    @DisplayName("A User can be instantiated")
    @Test
    void userFactoryReturnsAnUserWhenTheParametersAreRight() {
        User user = new UserFactory().createUser("Jim", "Ken", "jk@here.dom", "there 123", "Pepito12", "12345678", "1111111111111111111111");
        assertNotNull(user);
        assertEquals("Jim", user.getName());
        assertEquals("Ken", user.getSurname());
        assertEquals("jk@here.dom", user.getEmail());
        assertEquals("there 123", user.getAddress());
        assertEquals("Pepito12", user.getPassword());
        assertEquals("12345678", user.getWalletAddress());
        assertEquals("1111111111111111111111", user.getCvu());
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheNameIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser(null, "Ken", "jk@here.dom", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheSurnameIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", null, "jk@here.dom", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheEmailIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", null, "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheAddressIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", null, "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenThePasswordIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "there 123", null, "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheWalletAddressIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "there 123", "Pepito12", null, "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheCVUIsNull() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "there 123", "Pepito12", "12345678", null);
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}