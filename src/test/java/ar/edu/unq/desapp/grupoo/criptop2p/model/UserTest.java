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
import java.math.BigDecimal;
import java.util.Set;


@DisplayName("User Tests")
@SpringBootTest
class UserTest {
    Validator validator;
    @BeforeEach
    void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("A User can be instantiated")
    @Test
    void userExist(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        assertNotNull(user);
        assertEquals("Jim", user.getName());
        assertEquals("Ken", user.getSurname());
        assertEquals("jk@here.dom", user.getEmail());
        assertEquals("1234567890", user.getAddress());
        assertEquals("Pepito12!", user.getPassword());
        assertEquals("12345678", user.getWalletAddress());
        assertEquals("1111111111111111111111",user.getCvu());
    }

    @Test
    void userCanChangeTheName(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito@12", "12345678", "1111111111111111111111");
        assertEquals("Jim", user.getName());
        user.setName("Pepe");
        assertEquals("Pepe", user.getName());
    }

    @Test
    void userCanChangeTheNameAndCheckContraint(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito@12", "12345678", "1111111111111111111111");
        assertEquals("Jim", user.getName());
        user.setName("Pepe");
        assertEquals("Pepe", user.getName());
    }



    @Test
    void userCanChangeTheSurame(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito,12", "12345678", "1111111111111111111111");
        assertEquals("Ken", user.getSurname());
        user.setSurname("Pepe");
        assertEquals("Pepe", user.getSurname());
    }

    @Test
    void userCanChangeTheEmail(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito;12", "12345678", "1111111111111111111111");
        assertEquals("jk@here.dom", user.getEmail());
        user.setEmail("jk@other.com");
        assertEquals("jk@other.com", user.getEmail());
    }


    @Test
    void userCanChangeThePassword(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito_12", "12345678", "1111111111111111111111");
        assertEquals("Pepito_12", user.getPassword());
        user.setPassword("Pepita12");
        assertEquals("Pepita12", user.getPassword());
    }


    @Test
    void userCanChangeTheWalletAddress(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito#12", "12345678", "1111111111111111111111");
        assertEquals("12345678", user.getWalletAddress());
        user.setWalletAddress("87654321");
        assertEquals("87654321", user.getWalletAddress());
    }

    @Test
    void userCanChangeTheAddress(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito.12", "12345678", "1111111111111111111111");
        assertEquals("1234567890", user.getAddress());
        user.setAddress("0987654321");
        assertEquals("0987654321", user.getAddress());
    }

    @Test
    void userCanChangeTheCVU(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito'12", "12345678", "1111111111111111111111");
        assertEquals("1111111111111111111111", user.getCvu());
        user.setCvu("2222222222222222222222");
        assertEquals("2222222222222222222222", user.getCvu());
    }


    @Test
    void userCreateConsistlyDontBrokeAnyContraints(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito+12", "12345678", "1111111111111111111111");
        assertNotNull(user);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheNameIsNull() {
        User userNullName = new User(null, "Ken", "jk@here.dom", "1234567890", "Pepito,12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullName);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheSurnameIsNull() {
        User userNullName = new User("Jim", null, "jk@here.dom", "1234567890", "Pepito.12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullName);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheEmailIsNull() {
        User userNullEmail = new User("Jim", "Ken", null, "1234567890", "Pepito12;", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullEmail);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheAddressIsNull() {
        User userNullAddress = new User("Jim", "Ken", "jc@gmail.com", null, "Pepito_12", "12345678", "1111111111111111111111");
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
        User userNullWalletAddress = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito!12", null, "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNullWalletAddress);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheCVUIsNull() {
        User userNullCVU = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito.12", "12345678", null);
        Set<ConstraintViolation<User>> violations = validator.validate(userNullCVU);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheNameIsEmpty() {
        User userEmptyName = new User("", "Ken", "jk@here.dom", "1234567890", "Pepito12*", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyName);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheSurnameIsEmpty() {
        User userEmptySurname = new User("Jim", "", "jk@here.dom", "1234567890", "Pepito!12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptySurname);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheEmailIsEmpty() {
        User userEmptyEmail = new User("Jim", "Ken", "", "1234567890", "Pepito1.2", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyEmail);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheAddressIsEmpty() {
        User userEmptyAddress = new User("Jim", "Ken", "jc@gmail.com", "", "Pepito+12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyAddress);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenThePasswordIsEmpty(){
        User userEmptyPassword = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyPassword);
        assertEquals(2, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheWalletAddressIsEmpty() {
        User userEmptyWalletAddress = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito.12", "", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyWalletAddress);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNullParameterExceptionWhenTheCVUIsEmpty() {
        User userEmptyCVU = new User("Jim", "Ken", "jc@gmail.com", "1234567890", "Pepito_12", "12345678", "");
        Set<ConstraintViolation<User>> violations = validator.validate(userEmptyCVU);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheNameHas2charLess() {
        User userNameLess = new User("Ho", "Ken", "jk@here.dom", "1234567890", "Pepito!12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsExceptionParameterWhenTheNameHas1char() {
        User userNameLess = new User("H", "Ken", "jk@here.dom", "1234567890", "Pepito$12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userNameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterWhenTheSurnameHas1charLess() {
        User userSurnameLess = new User("Jim", "V", "jc@gmail.com", "1234567890", "Pepito%12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHas2char() {
        User userSurnameLess = new User("Jim", "V", "jc@gmail.com", "1234567890", "Pepito*12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheNameHasMore30chars() {
        User userSurnameLess = new User("1234567890123456789012345678901", "Kem", "jc@gmail.com", "1234567890", "Pepito+12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHasMore30chars() {
        User userSurnameLess = new User("Jim", "1234567890123456789012345678901", "jc@gmail.com", "1234567890", "Pepito.12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userSurnameLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnIncorrectFormatExceptionMailWhenTheFormatMailIsIncorrect() {
        User userInvalidEmail = new User("Jim", "Ken","user#domain.com", "1234567890","Pepito#12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalidEmail);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasLess10chars() {
        User userAddressLess = new User("Jim", "Kem", "jc@gmail.com", "123456789", "Pepito.12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressLess);
        assertEquals(1, violations.size());
    }


    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasMore31chars() {
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890123456789012345678901", "Pepito.12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenThePasswordHasLess6chars() {
        User userPasswordLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "12345", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userPasswordLess);
        assertEquals(2, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenThePasswordDontRespectTheCapsFormat() {
        User userPasswordLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "pepito12", "12345678", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userPasswordLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheWalletAddressHasLess8chars() {
        User userWalletAddressLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito.12", "1234567", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userWalletAddressLess);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheWalletAddressHasMore8chars() {
        User userWalletAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito,12", "123456789", "1111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userWalletAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheCVUHasLess22chars() {
        User userAddressLess = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito.12", "12345678", "111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressLess);
        assertEquals(1, violations.size());
    }


    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasMore22chars() {
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito++12", "12345678", "11111111111111111111111");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithOnlyAnUpperCaseButCheckExtensionBrokesAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithAllItsCharsInUpperCaseButCheckExtensionBrokesAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "PEPITO", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithAllItsCharsInLowerCaseButCheckExtensionBrokesAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "pepito", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithOnlyAnLowerCaseButCheckExtensionBrokesAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "pepito", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithAnLowerAndUpperCaseAndCheckExtensionBrokesAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithAnLowerAndUpperCaseAndCheckExtensionAndADigitBrokesAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito1", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(1, violations.size());
    }

    @Test
    void testAPasswordWithAnLowerAndUpperCaseAndCheckExtensionAndADigitAndASpecialCharacterDonntBrokeAContrait(){
        User userAddressMore = new User("Jim", "Kem", "jc@gmail.com", "1234567890", "Pepito.1", "12345678", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(userAddressMore);
        assertEquals(0, violations.size());
    }

    @Test
    void testToBeCreatedAnUserHasNotAnyOffer(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        assertEquals(0, user.getOffers().size());
    }

    @Test
    void testToOfferAnUserReturnAnIntentionWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        Intention intention = user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(user, intention.getUser());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(10, intention.getCount());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.CAKEUSDT, intention.getCrypto());
    }

    @Test
    void testTheUserCanAddOffersToItsListOfOffersWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        assertEquals(0, user.getOffers().size());
        user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(1, user.getOffers().size());
        user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(2, user.getOffers().size());
    }

    @Test
    void testToOfferAnUserAddAnIntentionToItsListOfIntentionsWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        assertEquals(0, user.getOffers().size());
        user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(1, user.getOffers().size());
    }

    @Test
    void testToOfferAnUserAddAnIntentionToItsListOfIntentionsAndItIsTheExpectedWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        Intention intention = user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        Intention uniqueIntention = user.getOffers().get(0);
        assertEquals(intention.getUser(), uniqueIntention.getUser());
        assertEquals(intention.getPrice(), uniqueIntention.getPrice());
        assertEquals(intention.getCount(), uniqueIntention.getCount());
        assertEquals(intention.getType(), uniqueIntention.getType());
        assertEquals(intention.getCrypto(), uniqueIntention.getCrypto());
    }


    @Test
    void testAnUserCanAcceptAnIntention(){
        User anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.BUY, CryptoName.ETHUSDT,new BigDecimal(2));
        otherUser.accept(intention, new BigDecimal(2));

    }

}


