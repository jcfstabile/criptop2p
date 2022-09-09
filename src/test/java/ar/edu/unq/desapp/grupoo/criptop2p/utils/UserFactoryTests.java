package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.IncorrectFormatMail;
import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.NoExtensionsParameter;
import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.NullParameterException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.junit.jupiter.api.ClassOrderer;
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

    /*
    void checkIfCreaterUserThrowsNullParameterException(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu) {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu);
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    */

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

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheNameIsEmpty() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("", "Ken", "jk@here.dom", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheSurnameIsEmpty() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "", "jk@here.dom", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheEmailIsEmpty() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheAddressIsEmpty() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenThePasswordIsEmpty(){
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "there 123", "", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheWalletAddressIsEmpty() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "there 123", "Pepito12", "", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNullParameterExceptionWhenTheCVUIsEmpty() {
        Exception exception = assertThrows(NullParameterException.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "jc@gmail.com", "there 123", "Pepito12", "12345678", "");
            ;
        });
        String expectedMessage = "Some parameter/s is/are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNoExtensionsParameterExceptionWhenTheNameHas1charLess() {
        Exception exception = assertThrows(NoExtensionsParameter.class, () -> {
            new UserFactory().createUser("H", "Ken", "jc@gmail.com", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter does not meet the required extension";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNoExtensionsExceptionParameterWhenTheNameHas2char() {
        Exception exception = assertThrows(NoExtensionsParameter.class, () -> {
            new UserFactory().createUser("Ho", "Ken", "jc@gmail.com", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter does not meet the required extension";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNoExtensionsParameterWhenTheSurnameHas1charLess() {
        Exception exception = assertThrows(NoExtensionsParameter.class, () -> {
            new UserFactory().createUser("Jim", "V", "jc@gmail.com", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter does not meet the required extension";;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHas2char() {
        Exception exception = assertThrows(NoExtensionsParameter.class, () -> {
            new UserFactory().createUser("Jim", "Va", "jc@gmail.com", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "Some parameter does not meet the required extension";;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void userFactoryThrowsAnIncorrectFormatExceptionMailWhenTheFormatMailIsIncorrect() {
        Exception exception0 = assertThrows(IncorrectFormatMail.class, () -> {
            new UserFactory().createUser("Jim", "Ken","user#domain.com", "there 123","Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        Exception exception1 = assertThrows(IncorrectFormatMail.class, () -> {
            new UserFactory().createUser("Jim", "Ken", "@yahoo.com", "there 123", "Pepito12", "12345678", "1111111111111111111111");
            ;
        });
        String expectedMessage = "The email format is incorrect";
        String actualMessage0 = exception0.getMessage();
        String actualMessage1 = exception0.getMessage();
        assertTrue(actualMessage0.contains(expectedMessage));
        assertTrue(actualMessage1.contains(expectedMessage));
    }
}