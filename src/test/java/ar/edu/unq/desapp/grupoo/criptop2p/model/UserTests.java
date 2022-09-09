package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


@DisplayName("Model Tests")
@SpringBootTest
class UserTests {
    Validator validator;
    @BeforeEach
    void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("A User can be instantiated")
    @Test
    void userExist(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertNotNull(user);
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheNameIsNull() {
        User userNullName = new User(null, "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullName);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheSurnameIsNull() {
        User userNullName = new User("Jim", null, "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullName);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheEmailIsNull() {
        User userNullEmail = new User("Jim", "Ken", null, "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullEmail);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheAddressIsNull() {
        User userNullAddress = new User("Jim", "Ken", "jc@gmail.com", null, "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullAddress);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenThePasswordIsNull() {
        User userNullPassword = new User("Jim", "Ken", "jc@gmail.com", "1234567890", null, "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullPassword);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheWalletAddressIsNull() {
        User userNullWalletAddress = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito12", null, "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullWalletAddress);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheCVUIsNull() {
        User userNullCVU = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito12", "12345678", null);
        Set<ConstraintViolation<User>> violations = validator.validate(userNullCVU);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheNameIsEmpty() {
        User userEmptyName = new User("", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyName);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheSurnameIsEmpty() {
        User userEmptySurname = new User("Jim", "", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptySurname);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheEmailIsEmpty() {
        User userEmptyEmail = new User("Jim", "Ken", "", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyEmail);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheAddressIsEmpty() {
        User userEmptyAddress = new User("Jim", "Ken", "jc@gmail.com", "", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyAddress);
        assertEquals(1, violations.size());
    }


    @Test
    void userThrowsAnNullParameterExceptionWhenThePasswordIsEmpty(){
        User userEmptyPassword = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyPassword);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheWalletAddressIsEmpty() {
        User userEmptyWalletAddress = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito12", "", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyWalletAddress);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheCVUIsEmpty() {
        User userEmptyCVU = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyCVU);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheNameHas2charLess() {
        User userNameLess = new User("Ho", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsExceptionParameterWhenTheNameHas1char() {
        User userNameLess = new User("H", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterWhenTheSurnameHas1charLess() {
        User userSurnameLess = new User("Jim", "V", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHas2char() {
        User userSurnameLess = new User("Jim", "V", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnIncorrectFormatExceptionMailWhenTheFormatMailIsIncorrect() {
        User userInvalidEmail = new User("Jim", "Ken","user#domain.com", "1234567890","Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalidEmail);
        assertEquals(1, violations.size());
    }
}

