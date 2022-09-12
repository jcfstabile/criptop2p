package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.ConstraintViolation;
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
        assertEquals("Jim", user.getName());
        assertEquals("Ken", user.getSurname());
        assertEquals("jk@here.dom", user.getEmail());
        assertEquals("1234567890", user.getAddress());
        assertEquals("Pepito12", user.getPassword());
        assertEquals("12345678", user.getWalletAddress());
        assertEquals("1111111111111111111111",user.getCvu());
    }

    @Test
    void userCanChangeTheName(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("Jim", user.getName());
        user.setName("Pepe");
        assertEquals("Pepe", user.getName());
    }

    @Test
    void userCanChangeTheSurame(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("Ken", user.getSurname());
        user.setSurname("Pepe");
        assertEquals("Pepe", user.getSurname());
    }

    @Test
    void userCanChangeTheEmail(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("jk@here.dom", user.getEmail());
        user.setEmail("jk@other.com");
        assertEquals("jk@other.com", user.getEmail());
    }


    @Test
    void userCanChangeThePassword(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("Pepito12", user.getPassword());
        user.setPassword("Pepita12");
        assertEquals("Pepita12", user.getPassword());
    }


    @Test
    void userCanChangeTheWalletAddress(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("12345678", user.getWalletAddress());
        user.setWalletAddress("87654321");
        assertEquals("87654321", user.getWalletAddress());
    }

    @Test
    void userCanChangeTheAddress(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("1234567890", user.getAddress());
        user.setAddress("0987654321");
        assertEquals("0987654321", user.getAddress());
    }

    @Test
    void userCanChangeTheCVU(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertEquals("1111111111111111111111", user.getCvu());
        user.setCvu("2222222222222222222222");
        assertEquals("2222222222222222222222", user.getCvu());
    }


    @Test
    void userCreateConsistlyDontBrokeAnyContraints(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        assertNotNull(user);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
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
    void userThrowsAnNoExtensionsParameterExceptionWhenTheNameHasMore30chars() {
        User userSurnameLess = new User("1234567890123456789012345678901", "Kem", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHasMore30chars() {
        User userSurnameLess = new User("Jim", "1234567890123456789012345678901", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnIncorrectFormatExceptionMailWhenTheFormatMailIsIncorrect() {
        User userInvalidEmail = new User("Jim", "Ken","user#domain.com", "1234567890","Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalidEmail);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasLess10chars() {
        User userAddressLess = new User("Jim", "Kem", "jc@gmail.com", "123456789", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressLess);
        assertEquals(1, violations.size());
    }


    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasMore31chars() {
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890123456789012345678901", "Pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenThePasswordHasLess6chars() {
        User userPasswordLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "12345", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userPasswordLess);
        assertEquals(1, violations.size());
    }

    //ACA FALTA H DEL FUTURO
    //@Test
    //void userThrowsAnNoExtensionsParameterExceptionWhenThePasswordDontRespectTheCapsFormat() {
    //    User userPasswordLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "pepito12", "12345678", "1111111111111111111111");
    //    Set<ConstraintViolation<User>> violations = validator.validate(userPasswordLess);
    //    assertEquals(1, violations.size());
    //}

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheWalletAddressHasLess8chars() {
        User userWalletAddressLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito12", "1234567", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userWalletAddressLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheWalletAddressHasMore8chars() {
        User userWalletAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito12", "123456789", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userWalletAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheCVUHasLess22chars() {
        User userAddressLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressLess);
        assertEquals(1, violations.size());
    }


    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasMore22chars() {
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito12", "12345678", "11111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }
}


