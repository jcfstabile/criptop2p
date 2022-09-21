package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("User Tests")
@SpringBootTest
class UserTest {

    Validator validator;
    UserBuilder anyUser;
    @BeforeEach
    void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        anyUser = new UserBuilder();
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
        Intention intention = user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));

        assertEquals(user, intention.getOffered());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(10, intention.getCount());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.CAKEUSDT, intention.getCrypto());
    }

    @DisplayName("When the Intention price is in range the user can make the offer")
    @Test
    void testTheUserCanAddOffersToItsListOfOffersWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();
        assertEquals(0, user.getOffers().size());
        user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(1, user.getOffers().size());
        user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(2, user.getOffers().size());
    }

    @DisplayName("When the Intention price is in range the user can make the offer")
    @Test
    void testToOfferAnUserAddAnIntentionToItsListOfIntentionsWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();

        assertEquals(0, user.getOffers().size());
        user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));
        assertEquals(1, user.getOffers().size());
    }

    @DisplayName("When a User offer the intention is added to its offers list")
    @Test
    void testToOfferAnUserAddAnIntentionToItsListOfIntentionsAndItIsTheExpectedWhenThePriceIsBeetweenInTheRange5PerCentMoreAndLess(){
        User user = anyUser.build();
        Intention intention = user.offer(10, new BigDecimal(2), Type.SELL, CryptoName.CAKEUSDT, new BigDecimal(2));

        assertEquals(intention, user.getOffers().get(0));
    }


    @DisplayName("When a User accept other User intention the first turns on demander")
    @Test
    void testAnUserCanAcceptAnIntentionSetItsDemander(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        BigDecimal offerPrice = new BigDecimal(2);
        BigDecimal quationOnOfferTime = new BigDecimal(2);
        BigDecimal quationOnAcceptTime = new BigDecimal(2);

        Intention intention = anUser.offer(1, offerPrice, Type.BUY, CryptoName.ETHUSDT, quationOnOfferTime);

        assertNull(intention.getDemander());
        otherUser.accept(intention, quationOnAcceptTime);
        assertNotNull(intention.getDemander());
    }

    @DisplayName("When a User accept other User intention the first turns on demander")
    @Test
    void testWhenAnUserAcceptAnIntentionItsIsItsDemander(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        BigDecimal offerPrice = new BigDecimal(2);
        BigDecimal quationOnOfferTime = new BigDecimal(2);
        BigDecimal quationOnAcceptTime = new BigDecimal(2);
        Intention intention = anUser.offer(1, offerPrice, Type.BUY, CryptoName.ETHUSDT, quationOnOfferTime);

        otherUser.accept(intention, quationOnAcceptTime);

        assertEquals(otherUser, intention.getDemander());
    }

    @DisplayName("When a User accept an intention this status change to sold")
    @Test
    void testWhenAnUserAcceptAnIntentionThisChangeItsStatusToSOLD(){
        // User anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        // User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.BUY, CryptoName.ETHUSDT,new BigDecimal(2));
        assertEquals(Status.OFFERED, intention.getStatus());
        otherUser.accept(intention, new BigDecimal(2));
        assertEquals(Status.SOLD, intention.getStatus());
    }

    @DisplayName("When a User accept a buy intention and the current price is bigger than the intention offer it is canceled automatically")
    @Test
    void testWhenAnUserAcceptAnIntentionBuyButTheCurrentPriceIsBiggerTheDemanderChangeButTheIntentionIsCanceledBySystem(){
        // User anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        // User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.BUY, CryptoName.ETHUSDT,new BigDecimal(2));
        assertEquals(Status.OFFERED, intention.getStatus());
        assertNull(intention.getDemander());
        otherUser.accept(intention, new BigDecimal(42));
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
        assertNull(intention.getDemander());
    }

    @DisplayName("When a User accept a sell intention and the quoted price is higger that de intention price it is canceled automatically")
    @Test
    void testWhenAnUserAcceptAnIntentionSellButTheCurrentPriceIsBiggerTheDemanderChangeButTheIntentionIsCanceledBySystem(){
        // User anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        // User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();

        Intention intention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));
        assertNull(intention.getDemander());
        assertEquals(Status.OFFERED, intention.getStatus());
        otherUser.accept(intention, new BigDecimal(1));

        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
        assertNull(intention.getDemander());
    }

    @DisplayName("When an offerer cancel its intention this is marked with CANCELED status")
    @Test
    void testWhenAnOffererCancelItsIntentionThisIsMarkedWithCanceledStatus(){
//        User anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        User anUser = anyUser.withEmail("jk@here.dom").build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));

        assertEquals(Status.OFFERED, intention.getStatus());
        anUser.cancel(intention);
        assertEquals(Status.CANCELED, intention.getStatus());
    }

    @DisplayName("When a demander cancel an intention this is marked with OFFERED status")
    @Test
    void testWhenAndemanderCancelAnIntentionThisIsMarkedWithOFFEREDStatus(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));
        assertEquals(Status.OFFERED, intention.getStatus());
        otherUser.accept(intention, new BigDecimal(2));

        assertEquals(Status.SOLD, intention.getStatus());
        otherUser.cancel(intention);
        assertEquals(Status.OFFERED, intention.getStatus());
    }

    @DisplayName("When a demander cancel an intention this modificate this demander")
    @Test
    void testWhenAndemanderCancelAnIntentionThisModificateItsDemander(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));
        assertEquals(Status.OFFERED, intention.getStatus());
        assertNull(intention.getDemander());
        otherUser.accept(intention, new BigDecimal(2));
        assertNotNull(intention.getDemander());

        assertEquals(Status.SOLD, intention.getStatus());
        otherUser.cancel(intention);
        assertNull(intention.getDemander());
    }

    @DisplayName("When other User cancel the intention nothing change")
    @Test
    void testWhenOtherUserCancelTheIntentionNothingChange(){
        User anUser = anyUser.withEmail("jk@here.dom").build();
        User otherUser = anyUser.withEmail("asd@here.dom").build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));
        otherUser.accept(intention, new BigDecimal(2));
        User otherOtherUser = anyUser.withEmail("other@there.dom").build();

        otherOtherUser.cancel(intention);
        assertEquals(Status.SOLD, intention.getStatus());
        assertEquals(otherUser, intention.getDemander());
        assertEquals(anUser, intention.getOffered());
    }

    @DisplayName("Inicially a User has not operation")
    @Test
    void testIniciallyAnUserHasNotOperation(){
        User anUser = anyUser.build();

        assertEquals(0, anUser.quantityIntentions());
    }

    @DisplayName("Inicially a User has not points")
    @Test
    void testIniciallyAnUserHasNotPoints(){
        User anUser = anyUser.build();

        assertEquals(0, anUser.getPoints());
    }

    @DisplayName("When a User make a offer has one operation")
    @Test
    void testAnUserHasOneOperationWhenOfferOne(){
        User anUser = anyUser.build();
        Intention intention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));

        assertEquals(1, anUser.quantityIntentions());
    }

    @DisplayName("When a User offer two intentions has two operations")
    @Test
    void testAnUserHasOneOperationWhenOfferTwo(){
        User anUser = anyUser.build();
        anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));
        anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.ETHUSDT,new BigDecimal(2));

        assertEquals(2, anUser.quantityIntentions());
    }

    @DisplayName("an User initially has no reputation")
    @Test
    void testAnUserHasNotReputacionInicially(){
        User anUser = anyUser.build();

        assertEquals(0, anUser.getReputation());
    }

    @DisplayName("Status has a name")
    @Test
    void concept(){
        assertEquals("SOLD", Status.SOLD.name());
    }
}


