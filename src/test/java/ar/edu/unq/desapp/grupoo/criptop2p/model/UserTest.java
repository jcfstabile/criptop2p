package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.IntentionBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IncorrectUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@DisplayName("User Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserTest {

    Validator validator;
    UserBuilder anyUser;
    IntentionBuilder anyIntention;

    @BeforeEach
    void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        anyUser = new UserBuilder("aaa","bbb","c@d.e","fghijklmno", "Pqrs7$", "12345678","1234567890123456789012");
        anyIntention = new IntentionBuilder();
    }

    @DisplayName("A User can be instantiated")
    @Test
    void userExist(){
        User user = anyUser.withName("Jim")
                .withSurname("Ken")
                .withEmail("jk@here.dom")
                .withAddress("1234567890")
                .withPassword("Pepito12!")
                .withWalletAddress("12345678")
                .withCvu("1234567890123456789012")
                .build();
        assertNotNull(user);
        assertEquals("Jim", user.getName());
        assertEquals("Ken", user.getSurname());
        assertEquals("jk@here.dom", user.getEmail());
        assertEquals("1234567890", user.getAddress());
        assertEquals("Pepito12!", user.getPassword());
        assertEquals("12345678", user.getWalletAddress());
        assertEquals("1234567890123456789012",user.getCvu());

    }

    @DisplayName("A User can change the name")
    @Test
    void userCanChangeTheName(){
        User user = anyUser.withName("Jim").build();

        user.setName("Pete");

        assertEquals("Pete", user.getName());
    }

    @DisplayName("A User can change the surname")
    @Test
    void userCanChangeTheSurame(){
        User user = anyUser.withSurname("Ken").build();

        user.setSurname("Ron");

        assertEquals("Ron", user.getSurname());
    }

    @DisplayName("A User can change the email")
    @Test
    void userCanChangeTheEmail(){
        User user = anyUser.withEmail("jk@here.dom").build();

        user.setEmail("jk@other.com");

        assertEquals("jk@other.com", user.getEmail());
    }


    @DisplayName("A User can change the password")
    @Test
    void userCanChangeThePassword(){
        User user = anyUser.withPassword("Pepito_12").build();

        user.setPassword("Pepita12");

        assertEquals("Pepita12", user.getPassword());
    }


    @DisplayName("A User can change the walletAddress")
    @Test
    void userCanChangeTheWalletAddress(){
        User user = anyUser.withWalletAddress("12345678").build();

        user.setWalletAddress("87654321");

        assertEquals("87654321", user.getWalletAddress());
    }

    @DisplayName("A User can change the address")
    @Test
    void userCanChangeTheAddress(){
        User user = anyUser.withAddress("1234567890").build();

        user.setAddress("0987654321");

        assertEquals("0987654321", user.getAddress());
    }

    @DisplayName("A User can change the CVU")
    @Test
    void userCanChangeTheCVU(){
        User user = anyUser.withCvu("1234567890123456789012").build();

        user.setCvu("2109876543210987654321");

        assertEquals("2109876543210987654321", user.getCvu());
    }


    @DisplayName("A User created consistently breaks no constraints")
    @Test
    void userCreateConsistlyDontBrokeAnyContraints(){
        User user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito+12", "12345678", "1111111111111111111111");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertNotNull(user);
        assertEquals(0, violations.size());
    }

    void assertExactlyOneConstraintViolationForWith(User user, String message){
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertEquals(message, violations.stream().findFirst().get().getMessage());

    }

    @DisplayName("When the name is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheNameIsNull() {
        User userNullName = anyUser.withName(null).build();

        assertExactlyOneConstraintViolationForWith(userNullName, "Name cannot be empty or null");
    }

    @DisplayName("When the surname is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheSurnameIsNull() {
        User userNullSurname = anyUser.withSurname(null).build();

        assertExactlyOneConstraintViolationForWith(userNullSurname, "Surname cannot be empty");
    }

    @DisplayName("When the email is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheEmailIsNull() {
        User userNullEmail = anyUser.withEmail(null).build();

        assertExactlyOneConstraintViolationForWith(userNullEmail, "Email cannot be empty");
    }

    @DisplayName("When the address is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheAddressIsNull() {
        User userNullAddress= anyUser.withAddress(null).build();

        assertExactlyOneConstraintViolationForWith(userNullAddress, "Address cannot be empty");
    }

    @DisplayName("When the password is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenThePasswordIsNull() {
        User userNullPassword = anyUser.withPassword(null).build();

        assertExactlyOneConstraintViolationForWith(userNullPassword, "Password cannot be empty");
    }

    @DisplayName("When the walletAddress is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheWalletAddressIsNull() {
        User userNullWalletAddress= anyUser.withWalletAddress(null).build();

        assertExactlyOneConstraintViolationForWith(userNullWalletAddress, "Wallet Address cannot be empty");
    }

    @DisplayName("When the cvu is null a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheCVUIsNull() {
        User userNullCVU= anyUser.withCvu(null).build();

        assertExactlyOneConstraintViolationForWith(userNullCVU, "CVU cannot be null");
    }

    @DisplayName("When the name is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheNameIsEmpty() {
        User userEmptyName = anyUser.withName("").build();

        assertExactlyOneConstraintViolationForWith(userEmptyName, "Name must have between 3 and 30 characters");
    }

    @DisplayName("When the surname is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheSurnameIsEmpty() {
        User userEmptySurname = anyUser.withSurname("").build();

        assertExactlyOneConstraintViolationForWith(userEmptySurname, "Surname must have between 3 and 30 characters");
    }

    @DisplayName("When the email is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheEmailIsEmpty() {
        User userEmptyEmail = anyUser.withEmail("").build();

        assertExactlyOneConstraintViolationForWith(userEmptyEmail, "Email is not valid");
    }

    @DisplayName("When the address is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheAddressIsEmpty() {
        User userEmptyAddress = anyUser.withAddress("").build();

        assertExactlyOneConstraintViolationForWith(userEmptyAddress, "Address must have between 10 and 30 characters");
    }

    @DisplayName("When the password is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenThePasswordIsEmpty(){
        User userEmptyPassword = anyUser.withPassword("").build();

        assertExactlyOneConstraintViolationForWith(userEmptyPassword,
                """
                        Password must contain:

                        - At least one uppercase
                        - At least one lowercase
                        - At least one digit
                        - At least one special character
                        - Min 6 characters

                        """);
    }

    @DisplayName("When the walletAddress is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheWalletAddressIsEmpty() {
        User userEmptyWalletAddress = anyUser.withWalletAddress("").build();

        assertExactlyOneConstraintViolationForWith(userEmptyWalletAddress, "Wallet Address must have 8 characters");
    }

    @DisplayName("When the CVU is empty a User show a constraint violation ")
    @Test
    void userThrowsAnNullParameterExceptionWhenTheCVUIsEmpty() {
        User userEmptyCVU = anyUser.withCvu("").build();

        assertExactlyOneConstraintViolationForWith(userEmptyCVU, "CVU must have 22 characters");
    }

    @DisplayName("When the name shorter than 3 a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheNameHas2charLess() {
        User userShorterName = anyUser.withName("Ho").build();

        assertExactlyOneConstraintViolationForWith(userShorterName, "Name must have between 3 and 30 characters");
    }

    @DisplayName("When the name has 1 char a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsExceptionParameterWhenTheNameHas1char() {
        User userOneCharName = anyUser.withName("H").build();

        assertExactlyOneConstraintViolationForWith(userOneCharName, "Name must have between 3 and 30 characters");
    }

    @DisplayName("When the surname has 1 char a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterWhenTheSurnameHas1charLess() {
        User userOneCharSurname = anyUser.withSurname("V").build();

        assertExactlyOneConstraintViolationForWith(userOneCharSurname, "Surname must have between 3 and 30 characters");
    }


    @DisplayName("When the surname has 2 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHas2char() {
        User userShorterSurname = anyUser.withSurname("Vo").build();

        assertExactlyOneConstraintViolationForWith(userShorterSurname, "Surname must have between 3 and 30 characters");
    }

    @DisplayName("When the name has more than 30 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheNameHasMore30chars() {
        User userLongestName = anyUser.withName("1234567890123456789012345678901").build();

        assertExactlyOneConstraintViolationForWith(userLongestName, "Name must have between 3 and 30 characters");
    }

    @DisplayName("When the surname has more than 30 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheSurnameHasMore30chars() {
        User userLongestSurname = anyUser.withSurname("1234567890123456789012345678901").build();

        assertExactlyOneConstraintViolationForWith(userLongestSurname, "Surname must have between 3 and 30 characters");
    }

    @DisplayName("When the email has an incorrect format a User show a constraint violation ")
    @Test
    void userThrowsAnIncorrectFormatExceptionMailWhenTheFormatMailIsIncorrect() {
        User userInvalidEmail = anyUser.withEmail("user#domain.com").build();

        assertExactlyOneConstraintViolationForWith(userInvalidEmail, "Email is not valid");
    }

    @DisplayName("When the address has less than 10 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasLess10chars() {
        User userAddressLess = anyUser.withAddress("123456789").build();

        assertExactlyOneConstraintViolationForWith(userAddressLess, "Address must have between 10 and 30 characters");
    }


    @DisplayName("When the address has more than 30 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasMore31chars() {
        User userAddressMore = anyUser.withAddress("1234567890123456789012345678901").build();

        assertExactlyOneConstraintViolationForWith(userAddressMore, "Address must have between 10 and 30 characters");
    }

    @DisplayName("@ When the password is not allowed a User show a constraint violation ")
    @ParameterizedTest
    @ValueSource(strings = {"", "xx$456", "XX$456", "Xx$abc", "Xxa456", "Xx$45", "Pep$0", "PEPITO", "pepito", "pepit", "Pepito", "Pepito1"})
    void userThrowsAnNoExtensionsParameterExceptionWhenThePasswordHasLess6chars(String notValidPassword) {
        User userInvalidPassword = anyUser.withPassword(notValidPassword).build();

        assertExactlyOneConstraintViolationForWith(userInvalidPassword,
                """
                        Password must contain:

                        - At least one uppercase
                        - At least one lowercase
                        - At least one digit
                        - At least one special character
                        - Min 6 characters

                        """);
    }

    @DisplayName("When the password has an lower, an uppercase, a digit, a special char and at least 6 chars a User show no constraint violation ")
    @Test
    void testAPasswordWithAnLowerAndUpperCaseAndCheckExtensionAndADigitAndASpecialCharacterDonntBrokeAContrait(){
        User userWithPassword = anyUser.withPassword("Pepit.0").build();

        Set<ConstraintViolation<User>> violations = validator.validate(userWithPassword);

        assertEquals(0, violations.size());
    }



    @DisplayName("When the walletAddresss has less than 8 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheWalletAddressHasLess8chars() {
        User userWalletAddressLess = anyUser.withWalletAddress("1234567").build();

        assertExactlyOneConstraintViolationForWith(userWalletAddressLess, "Wallet Address must have 8 characters");
    }

    @DisplayName("When the walletAddresss has more than 8 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheWalletAddressHasMore8chars() {
        User userWalletAddressMore = anyUser.withWalletAddress("123456789").build();

        assertExactlyOneConstraintViolationForWith(userWalletAddressMore, "Wallet Address must have 8 characters");
    }

    @DisplayName("When the cvu has less than 22 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheCVUHasLess22chars() {
        User userShortCvu= anyUser.withCvu("1234567890123456789012345678901").build();

        assertExactlyOneConstraintViolationForWith(userShortCvu, "CVU must have 22 characters");
    }

    @DisplayName("When the cvu has more than 22 chars a User show a constraint violation ")
    @Test
    void userThrowsAnNoExtensionsParameterExceptionWhenTheAddressHasMore22chars() {
        User userLargeCvu= anyUser.withCvu("123456789012345678901234567890123").build();

        assertExactlyOneConstraintViolationForWith(userLargeCvu, "CVU must have 22 characters");
    }

    @DisplayName("When a User is created has no offers")
    @Test
    void testToBeCreatedAnUserHasNotAnyOffer(){
        User user = anyUser.build();

        assertEquals(0, user.getOffers().size());
    }

    @DisplayName("When a user offer give a intention when the price is in the +- 5% range")
    @Test
    void testToOfferAnUserReturnAnIntentionWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();
        Intention intention = anyIntention.withUser(user).withCount(10).withPrice(2.0).withCrypto(CryptoName.CAKEUSDT).withType(new Sell()).build();

        user.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(user, intention.getOffered());
        assertEquals(BigDecimal.valueOf(2.0), intention.getPrice());
        assertEquals(10, intention.getCount());
        assertInstanceOf(Sell.class, intention.getType());
        assertEquals(CryptoName.CAKEUSDT, intention.getCrypto());
    }

    @DisplayName("When the Intention price is in range the user can make several offers")
    @Test
    void testTheUserCanAddOffersToItsListOfOffersWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();
        Intention intention = anyIntention.withUser(user).withPrice(2.0).build();

        assertEquals(0, user.getOffers().size());
        user.offer(intention, BigDecimal.valueOf(2.0));
        assertEquals(1, user.getOffers().size());
        user.offer(intention, BigDecimal.valueOf(2.0));
        assertEquals(2, user.getOffers().size());
    }

    @DisplayName("When the Intention price is in range the user can make the offer")
    @Test
    void testToOfferAnUserAddAnIntentionToItsListOfIntentionsWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();
        Intention intention = anyIntention.withUser(user).withPrice(2.0).build();

        assertEquals(0, user.getOffers().size());
        user.offer(intention, BigDecimal.valueOf(2.0));
        assertEquals(1, user.getOffers().size());
    }

    @DisplayName("When a User offer the intention is added to its offers list")
    @Test
    void testToOfferAnUserAddAnIntentionToItsListOfIntentionsAndItIsTheExpectedWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();
        Intention intention = anyIntention.withUser(user).withPrice(2.0).build();

        user.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(intention, user.getOffers().get(0));
    }

    @DisplayName("When a User accept an intention this status change to sold")
    @Test
    void testWhenAnUserAcceptAnIntentionThisChangeItsStatusToSOLD(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(Status.OFFERED, intention.getStatus());

        otherUser.accept(intention, new BigDecimal(2));

        assertEquals(Status.SOLD, intention.getStatus());
    }


    @DisplayName("When a User accept an intention this is in his offers list")
    @Test
    void testWhenAnUserAcceptAnIntentionThisGoToHisOffersList(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(0 , otherUser.getOffers().size());

        otherUser.accept(intention, BigDecimal.valueOf(2.0));

        assertEquals(1 , otherUser.getOffers().size());
    }

    @DisplayName("When a User accept a buy intention and the current price is bigger than the intention offer it is canceled automatically")
    @Test
    void testWhenAnUserAcceptAnIntentionBuyButTheCurrentPriceIsBiggerTheDemanderChangeButTheIntentionIsCanceledBySystem(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).withType(new Buy()).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(Status.OFFERED, intention.getStatus());
        otherUser.accept(intention, new BigDecimal(42));
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }

    @DisplayName("When a User accept a sell intention and the quoted price is higger that de intention price it is canceled automatically")
    @Test
    void testWhenAnUserAcceptAnIntentionSellButTheCurrentPriceIsBiggerTheDemanderChangeButTheIntentionIsCanceledBySystem(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(Status.OFFERED, intention.getStatus());
        otherUser.accept(intention, new BigDecimal(1));
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }

    @DisplayName("When an offered cancel its intention this is marked with CANCELED status")
    @Test
    void testWhenAnOffererCancelItsIntentionThisIsMarkedWithCanceledStatus() {
        User anUser = anyUser.withEmail("jk@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(Status.OFFERED, intention.getStatus());
        anUser.cancel(intention);
        assertEquals(Status.CANCELED, intention.getStatus());
    }

    @DisplayName("When a demander cancel an intention this is marked with OFFERED status")
    @Test
    void testWhenAndemanderCancelAnIntentionThisIsMarkedWithOFFEREDStatus(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(Status.OFFERED, intention.getStatus());

        otherUser.accept(intention, new BigDecimal(2));

        assertEquals(Status.SOLD, intention.getStatus());
        otherUser.cancel(intention);
        assertEquals(Status.OFFERED, intention.getStatus());
    }

    @DisplayName("When a demander cancel an intention this modify this demander")
    @Test
    void testWhenAnDemanderCancelAnIntentionThisModifyItsDemander(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(Status.OFFERED, intention.getStatus());
        otherUser.accept(intention, new BigDecimal(2));
        assertEquals(Status.SOLD, intention.getStatus());

        otherUser.cancel(intention);

        assertEquals(Status.OFFERED, intention.getStatus());
    }

    @DisplayName("When other User cancel the intention nothing change")
    @Test
    void testWhenOtherUserCancelTheIntentionNothingChange(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
//        Intention intention = anUser.offer(1, new BigDecimal(2), new Sell(), CryptoName.ETHUSDT,new BigDecimal(2));
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();
        anUser.offer(intention, BigDecimal.valueOf(2.0));
        otherUser.accept(intention, new BigDecimal(2));
        User otherOtherUser = anyUser.withEmail("other@there.dom").build();

        assertEquals(Status.SOLD, intention.getStatus());

        otherOtherUser.cancel(intention);

        assertEquals(Status.SOLD, intention.getStatus());
        assertEquals(anUser, intention.getOffered());
    }

    @DisplayName("Initially a User has not operation")
    @Test
    void testIniciallyAnUserHasNotOperation(){
        User anUser = anyUser.build();

        assertEquals(0, anUser.quantityIntentionsSold());
    }

    @DisplayName("Inicially a User has not points")
    @Test
    void testIniciallyAnUserHasNotPoints(){
        User anUser = anyUser.build();

        assertEquals(0, anUser.getPoints());
    }

    @DisplayName("When a User make a offer hasn't one operation")
    @Test
    void testAnUserHasNoOneMadeOperationWhenOfferOne(){
        User anUser = anyUser.build();
        assertEquals(0, anUser.quantityIntentionsSold());
    }

    @DisplayName("When a User offer two intentions hasn't any operations")
    @Test
    void testAnUserHasOneOperationWhenOfferTwo(){
        User anUser = anyUser.build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));
        anUser.offer(intention, BigDecimal.valueOf(2.0));

        assertEquals(0, anUser.quantityIntentionsSold());
    }



    @DisplayName("When a User make a offer has one operation when it is sold")
    @Test
    void testAnUserHasOneMadeOperationWhenOfferOne(){
        User anUser = anyUser.build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();

        anUser.offer(intention, BigDecimal.valueOf(2.0));

        intention.sold();
        assertEquals(1, anUser.quantityIntentionsSold());
    }

    @DisplayName("When a User offer two intentions has two operations")
    @Test
    void testAnUserHasTwoOperationsWhenTwoFromItsOperationAreSolded(){
        User anUser = anyUser.build();
        Intention intention0 = anyIntention.withUser(anUser).withPrice(2.0).build();
        Intention intention1 = anyIntention.withUser(anUser).withPrice(2.0).build();
        anUser.offer(intention0, BigDecimal.valueOf(2.0));
        anUser.offer(intention1, BigDecimal.valueOf(2.0));

        intention0.sold();
        intention1.sold();

        assertEquals(2, anUser.quantityIntentionsSold());
    }

    @DisplayName("When a User canceled an intention it doesn't count")
    @Test
    void testAnUserHasNotOperationsWhenCanceledTheOne(){
        User anUser = anyUser.build();
        Intention intention = anyIntention.withUser(anUser).withPrice(2.0).build();
        anUser.offer(intention, BigDecimal.valueOf(2.0));

        intention.sold();
        assertEquals(1, anUser.quantityIntentionsSold());
        intention.canceled();
        assertEquals(0, anUser.quantityIntentionsSold());
    }

    @DisplayName("When a User cancel two intentions hasn't two operations")
    @Test
    void testAnUserHasAnyOperationsWhenItsOperationAreCanceled(){
        User anUser = anyUser.build();
        Intention intention0 = anyIntention.withUser(anUser).withPrice(2.0).build();
        Intention intention1 = anyIntention.withUser(anUser).withPrice(2.0).build();
        anUser.offer(intention0, BigDecimal.valueOf(2.0));
        anUser.offer(intention1, BigDecimal.valueOf(2.0));

        intention0.sold();
        intention1.sold();

        assertEquals(2, anUser.quantityIntentionsSold());

        intention0.canceled();
        intention1.canceled();

        assertEquals(0, anUser.quantityIntentionsSold());
    }

    @DisplayName("an User initially has no reputation")
    @Test
    void testAnUserHasNotReputacionInicially(){
        User anUser = anyUser.build();

        assertEquals(0, anUser.getReputation());
    }

    @DisplayName("An User can add an intention to his list of intentions")
    @Test
    void testAnUserCanAddAnIntentionToItsOffers(){
        User user = anyUser.build();
        assertEquals(0, user.quantityIntentionsSold());
        Intention anIntention = new Intention();
        anIntention.sold();
        user.addIntention(anIntention);
        assertEquals(1, user.quantityIntentionsSold());
    }

    @DisplayName("An User subtract N to its Points")
    @Test
    void testAnUserCanBePenalized(){
        User user = anyUser.build();
        assertEquals(0, user.getPoints());
        user.applyPenalty(10);
        assertEquals(-10, user.getPoints());
    }

    @DisplayName("An User add N to its Points")
    @Test
    void testAnUserCanBeRewarded(){
        User user = anyUser.build();
        assertEquals(0, user.getPoints());
        user.addPoints(10);
        assertEquals(10, user.getPoints());
    }

    @DisplayName("User return a empty list when hasnot any intention between these dates")
    @Test
    void testUserReturnsAEmptyListWhenHasNotAnyIntentionBetweenTheseDates(){
        User user = anyUser.build();
        Date init = mock(Date.class);
        Date end = mock(Date.class);
        List<Intention> intentionsBetween = user.offersBetween(init, end);
        assertEquals(0,intentionsBetween.size());
    }


    @DisplayName("User return a list with an element when has only one intention between these dates")
    @Test
    void testUserReturnsAListWthAnINtentionWhenHasONlyOneIntentionBetweenTheseDates(@Mock Intention intention0, @Mock Intention intention1){
        User user = anyUser.build();
        Date init = mock(Date.class);
        Date end = mock(Date.class);
        Mockito.lenient().when(intention0.isBetween(init, end)).thenReturn(true);
        Mockito.lenient().when(intention1.isBetween(init, end)).thenReturn(false);
        user.addIntention(intention0);
        user.addIntention(intention1);
        List<Intention> intentionsBetween = user.offersBetween(init, end);
        assertEquals(1,intentionsBetween.size());
        assertTrue(intentionsBetween.contains(intention0));
        assertFalse(intentionsBetween.contains(intention1));
    }

    @DisplayName("User return a list with 2 element when has two intentions between these dates")
    @Test
    void testUserReturnsAListWithTwoIntentionWhenHasTwoIntentionsBetweenTheseDates(@Mock Intention intention0, @Mock Intention intention1, @Mock Intention intention2){
        User user = anyUser.build();
        Date init = mock(Date.class);
        Date end = mock(Date.class);
        Mockito.lenient().when(intention0.isBetween(init, end)).thenReturn(true);
        Mockito.lenient().when(intention1.isBetween(init, end)).thenReturn(true);
        Mockito.lenient().when(intention2.isBetween(init, end)).thenReturn(false);
        user.addIntention(intention0);
        user.addIntention(intention1);
        user.addIntention(intention2);
        List<Intention> intentionsBetween = user.offersBetween(init, end);
        assertEquals(2,intentionsBetween.size());
        assertTrue(intentionsBetween.contains(intention0));
        assertTrue(intentionsBetween.contains(intention1));
        assertFalse(intentionsBetween.contains(intention2));
    }

    @DisplayName("When an User deliver a crypto on an intention the status change to waiting for transfer")
    @Test
    void testUserDeliverOnAnIntentionChangeStatusOfIntention(){
        User user = anyUser.build();
        Intention intention = anyIntention.withUser(user).withPrice(2.0).build();
        user.offer(intention, BigDecimal.valueOf(2.0));
        intention.sold();

        user.delivery(intention);

        assertEquals(Status.WAITINGFORTRANSFER, intention.getStatus());
    }

    @DisplayName("When an User pay for a interchange on an intention the status change to waiting for delivery")
    @Test
    void testUserTransferOnAnIntentionChangeStatusOfIntention(){
        User user = anyUser.build();
        Intention intention = anyIntention.withUser(user).withPrice(2.0).build();
        user.offer(intention, BigDecimal.valueOf(2.0));
        intention.sold();

        user.payment(intention);

        assertEquals(Status.WAITINGFORDELIVERY, intention.getStatus());
    }
    @DisplayName("When an User pay for a interchange and the delivery was done on an intention the status change to closed")
    @Test
    void testUserTransferOnAnIntentionAlreadyDeliveredChangeStatusOfIntentionToClosed(){
        User seller = anyUser.build();
        User buyer = anyUser.build();
        Intention intention = anyIntention.withUser(seller).withPrice(2.0).build();
        seller.offer(intention, BigDecimal.valueOf(2.0));
        intention.sold();

        seller.delivery(intention);
        buyer.payment(intention);

        assertEquals(Status.CLOSED, intention.getStatus());
    }

    @DisplayName("When an offered acepted its own intention throws an exception")
    @Test
    void testWhenOfferedAceptedItsOwnIntentionThrowsAnException(){
        User seller = new User(123456789L, "pepe", "juarez", "vato@mx.org", "PasswordSegura85+", "11112222", "2109876543210987654321");
        Intention intention = anyIntention.withUser(seller).withPrice(2.0).build();
        seller.offer(intention, BigDecimal.valueOf(2.0));
        BigDecimal price = new BigDecimal(2);
        IncorrectUserException exception = assertThrows(IncorrectUserException.class, () ->
                seller.accept(intention, price)
        );
        assertEquals("The user with ID: 123456789 cant accepted this intention because its is the intention offered", exception.getMessage());
    }

    @DisplayName("User can difference between it and other user")
    @Test
    void testUserCanDifferenceBetweenItAndOtherUser(){
        User seller = new User("pepe", "juarez", "vato@mx.org", "FakeSt 44332211", "PasswordSegura85+", "11112222", "2109876543210987654321");
        User buyer = anyUser.build();
        assertFalse(seller.isSameUser(buyer));
    }


    @DisplayName("User can identicate it")
    @Test
    void testUserIsSameuser(){
        User seller = anyUser.build();
        assertTrue(seller.isSameUser(seller));
    }
}

