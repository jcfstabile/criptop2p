package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.IntentionBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.StatusChangeErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Intention Tests")
@SpringBootTest
class IntentionTest {

    UserBuilder anyUser = new UserBuilder("aaa","bbb","c@d.e","fghijklmno", "Pqrs7$", "12345678","1234567890123456789012");

    IntentionBuilder anyIntention = new IntentionBuilder();
    User anUser, otherUser;
    Intention intention;
    Intention intentionBuy;
    Intention intentionSell;

    @BeforeEach
    void setUp() {
        anUser = anyUser.withEmail("he@here.dom").build();
        otherUser = anyUser.withEmail("asd@there.dom").build();
        intention = anyIntention.withUser(anUser).withType(new Sell()).withCrypto(CryptoName.ATOMUSDT).build();
        intentionBuy = anyIntention.withType(new Buy()).withCrypto(CryptoName.ATOMUSDT).build();
        intentionSell = anyIntention.withType(new Sell()).withCrypto(CryptoName.ATOMUSDT).build();
    }

    @DisplayName("An intention set its ID")
    @Test
    void testSetIntention(){
        Intention intentionToSet = anyIntention.withUser(anUser).withType(new Sell()).withCrypto(CryptoName.ATOMUSDT).build();
        intentionToSet.setId(1234567890L);
        assertEquals(1234567890L, intentionToSet.getId());
    }

    @DisplayName("An intention is between these dates")
    @Test
    void testAnIntentionIsBetweenTheseDates(){
        Date myDate = new Date();
        Date init = new Date(myDate.getTime() - 2);
        Intention intentionBetween = anyIntention.withUser(anUser).withType(new Sell()).withCrypto(CryptoName.ATOMUSDT).build();
        Date end = new Date(2022, 12,31);
        assertEquals("NOVEMBER", intentionBetween.timestamp.toLocalDateTime().toLocalDate().getMonth().name());
        assertEquals(2022, intentionBetween.timestamp.toLocalDateTime().toLocalDate().getYear());
        assertTrue(intentionBetween.isBetween(init, end));
    }


    @DisplayName("")
    @Test
    void testotherUserIsNotTheDemander(){
        User otherOtherUser = anyUser.withEmail("heasd@here.dom").build();
        String price = new BinanceIntegration().priceOf(CryptoName.ATOMUSDT).getPrice();
        BigDecimal currentPrice = new BigDecimal(price);
        anUser.offer(intention, currentPrice);
        assertFalse(intention.isItsDemander(anUser));
        otherUser.accept(intention, currentPrice);
        assertFalse(intention.isItsDemander(otherOtherUser));
    }



    @DisplayName("An intention is not between these dates")
    @Test
    void testAnIntentionIsNotBetweenTheseDates(){
        Intention intentionBetween = anyIntention.withUser(anUser).withType(new Sell()).withCrypto(CryptoName.ATOMUSDT).build();
        Date init = new Date(2000, 11,01);
        Date start = new Date(2000, 11,30);
        assertFalse(intentionBetween.isBetween(init, start));
    }

    @DisplayName("An Intention exist")
    @Test
    void testAnIntentionExist() {
        assertEquals(anUser, intention.getOffered());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(new Sell().getName(), intention.getType().getName());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
    }

    @DisplayName("An IntentionCreationDTO exist")
    @Test
    void testAnIntentionDTOExist() {
        IntentionCreationDTO intentionDTO = new IntentionCreationDTO(1, "2", "SELL", "ATOMUSDT");

        assertEquals(1, intentionDTO.getCount());
        assertEquals("2", intentionDTO.getPrice());
        assertEquals("SELL", intentionDTO.getType());
        assertEquals("ATOMUSDT", intentionDTO.getCryptoName());
    }

    @DisplayName("An Intention can change Status to CANCELED")
    @Test
    void testAnIntentionCanChangeStatusToCanceled(){
        assertEquals(Status.OFFERED, intention.getStatus());
        intention.canceled();
        assertEquals(Status.CANCELED, intention.getStatus());
    }

    @DisplayName("An Intention can change Status to CANCELEDBYSYSTEM")
    @Test
    void testAnIntentionCanChangeStatusToCanceledBySystem(){
        assertEquals(Status.OFFERED, intention.getStatus());
        intention.canceledBySystem();
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }

    @DisplayName("When the current price is bigger isBiggerThan is true")
    @Test
    void testIsBiggerThanReturnTrueWhenTheCurrentPriceIsBiggerThanPrice() {
        assertTrue(intention.isBiggerThan(new BigDecimal(3)));
    }

    @DisplayName("When the current price is smaller isSmallerThan is true")
    @Test
    void testIsSmallerThanReturnTrueWhenTheCurrentPriceIsSmallerThanPrice() {
        assertTrue(intention.isSmallerThan(new BigDecimal(1)));
    }

    @DisplayName("When the current price is not bigger isBiggerThan is false")
    @Test
    void testIsBiggerThanReturnFalseWhenTheCurrentPriceIsNotBiggerThanPrice() {
        assertFalse(intention.isBiggerThan(new BigDecimal("1.9")));
    }

    @DisplayName("When the current price is not smaller isSmallerThan is false")
    @Test
    void testIsSmallerThanReturnFalseWhenTheCurrentPriceIsNotSmallerThanPrice() {
        assertFalse(intention.isSmallerThan(new BigDecimal("2.1")));
    }

    @DisplayName("When the current price is same isBiggerThan is false")
    @Test
    void testIsBiggerThanReturnFalseWhenTheCurrentPriceIsEqualToPrice() {
        assertFalse(intention.isBiggerThan(new BigDecimal(2)));
    }

    @DisplayName("When the current price is same isSmallerThan is false")
    @Test
    void testIsSmallerThanReturnFalseWhenTheCurrentPriceIsEqualToPrice() {
        assertFalse(intention.isSmallerThan(new BigDecimal(2)));
    }

    @DisplayName("When the current price and the BUY Intention price are equals its do not change")
    @Test
    void testthePriceOfAnIntentionBuyDoesNotChangeWhenTheCurrentPriceAndThePriceAreEquals() {
        BigDecimal before = intentionBuy.getPrice();
        intentionBuy.verifyIfIsAcepted(new BigDecimal(2));
        BigDecimal after = intentionBuy.getPrice();

        assertEquals(before, after);
    }

    @DisplayName("When the current price and the SELL Intention price are equals its do not change")
    @Test
    void testthePriceOfAnIntentionSellDoesNotChangeWhenTheCurrentPriceAndThePriceAreEquals() {
        BigDecimal before = intentionSell.getPrice();
        intentionSell.verifyIfIsAcepted(new BigDecimal(2));
        BigDecimal after = intentionSell.getPrice();

        assertEquals(before, after);
    }

    @DisplayName("When the current price is smaller than BUY Intention's price its change")
    @Test
    void testthePriceOfAnIntentionBuyChangeWhenTheCurrentPriceIsSmaller() {
        BigDecimal before = intentionBuy.getPrice();

        assertEquals(new BigDecimal(2), before);
        intentionBuy.verifyIfIsAcepted(new BigDecimal(1));
        BigDecimal after = intentionBuy.getPrice();

        assertEquals(new BigDecimal(1), after);
    }

    @DisplayName("When the SELL Intention's price is bigger than current price do not change")
    @Test
    void testthePriceOfAnIntentionSellDoesNotChangeWhenTheCurrentPriceAndThePriceIsBigger() {
        BigDecimal before = intentionSell.getPrice();

        assertEquals(new BigDecimal(2), before);
        intentionSell.verifyIfIsAcepted(new BigDecimal(3));
        BigDecimal after = intentionSell.getPrice();

        assertEquals(new BigDecimal(3), after);
    }

    @DisplayName("An Intention can identificate if an User is its offerer")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsItsOfferer(){
        anUser.offer(intentionSell, BigDecimal.valueOf(2));

        assertTrue(intentionSell.isItsOfferer(anUser));
    }

    @DisplayName("An Intention can identificate if an User is not its offerer")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsNotItsOfferer(){
        anUser.offer(intentionSell, BigDecimal.valueOf(2));

        assertFalse(intentionSell.isItsOfferer(otherUser));
    }

    @DisplayName("An Intention can identificate if an User is its offered after accept it")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsItsOffered(){
        otherUser.accept(intentionSell, new BigDecimal(2));

        assertTrue(intentionSell.isItsOfferer(anUser));
    }

    @DisplayName("An Intention can identificate if an User is not its offered")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsNotItsOffered(){
        Intention someIntention = anyIntention.build();
        otherUser.accept(someIntention, new BigDecimal(2));

        assertFalse(someIntention.isItsOfferer(otherUser));
    }

    @DisplayName("An Intention has timestamp")
    @Test
    void testAnIntentionHasTimeStamp() {
        assertNotNull(intention.getTimeStamp());
    }

    @DisplayName("An Intention give 10 of reward when has been accepted before 30 minutes")
    @Test
    void testAnIntentionReturn10BecauseItHasAceptedBefore30minutes(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        assertEquals(10, intention.reward(now));
    }

    @DisplayName("An Intention give 10 of reward when has been accepted 30 minutes later")
    @Test
    void testAnIntentionReturn10BecauseItHasAceptedAt30minutesLater(){
        long minutesLater = intention.timestamp.getTime() + TimeUnit.MINUTES.toMillis(30);//30 minutes
        Timestamp now = new Timestamp(minutesLater);
        assertEquals(10, intention.reward(now));
    }

    @DisplayName("An Intention give 5 of reward when has been accepted after 30 minutes")
    @Test
    void testAnIntentionReturn10BecauseItHasAceptedLater30minutes(){
        long minuteslater = System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(31);
        Timestamp now = new Timestamp(minuteslater);
        assertEquals(5, intention.reward(now));
    }

    @DisplayName("An intention change its status to waiting for Transfer")
    @Test
    void testAnIntentionChangeItsStatusToWaitingForTransferToReceiveThisMessage(){
        intention.sold();
        assertEquals(Status.SOLD, intention.getStatus());

        intention.deliveryDone();

        assertEquals(Status.WAITINGFORTRANSFER, intention.getStatus());
    }


    @DisplayName("An intention change its status to waiting for Delivery")
    @Test
    void testAnIntentionChangeItsStatusToWaitingForDeliveryToReceiveThisMessage(){
        intention.sold();
        assertEquals(Status.SOLD, intention.getStatus());

        intention.transferDone();

        assertEquals(Status.WAITINGFORDELIVERY, intention.getStatus());
    }

    @DisplayName("When a transfer has been done and get the delivery the intention is closed")
    @Test
    void testAnIntentionChangeItsStatusToCloseIfTransferWasAlreadyDoneAndGetdelivery(){
        intention.sold();
        assertEquals(Status.SOLD, intention.getStatus());

        intention.transferDone();

        assertEquals(Status.WAITINGFORDELIVERY, intention.getStatus());

        intention.deliveryDone();

        assertEquals(Status.CLOSED, intention.getStatus());
    }

    @DisplayName("When a delivery has been done and get the transfer the intention is closed")
    @Test
    void testAnIntentionChangeItsStatusToCloseIfDeliveryWasAlreadyDoneAndGetTransfer(){
        intention.sold();
        assertEquals(Status.SOLD, intention.getStatus());

        intention.deliveryDone();

        assertEquals(Status.WAITINGFORTRANSFER, intention.getStatus());

        intention.transferDone();

        assertEquals(Status.CLOSED, intention.getStatus());
    }

    @DisplayName("When an intention was sell, its status changed to SOLD")
    @Test
    void testWhenAnIntentionHasSoledByOtherUserThisChangeItsStatusToSold(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        intention.sold(now, otherUser);
        assertEquals(Status.SOLD, intention.getStatus());
    }


    @DisplayName("When an intention was sell, before 30 minutes, its add 10 points to its offered")
    @Test
    void testWhenAnIntentionWasSoledBefore30MinutesItAdd10PointsToItsOffered(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        assertEquals(0, intention.getOffered().getPoints());
        intention.sold(now, otherUser);
        assertEquals(10, intention.getOffered().getPoints());
    }


    @DisplayName("When an intention was sell, before 30 minutes, its add 10 points to its demander")
    @Test
    void testWhenAnIntentionWasSoledBefore30MinutesItAdd10PointsToItsDemander(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        assertEquals(0, otherUser.getPoints());
        intention.sold(now, otherUser);
        assertEquals(10, otherUser.getPoints());
    }

    @DisplayName("When an intention was solded, after 30 minutes, its add 5 points to its offered")
    @Test
    void testWhenAnIntentionWasSoledAfter30MinutesItAdd5PointsToItsOffered(){
        Timestamp moment = new Timestamp(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(31));
        assertEquals(0, intention.getOffered().getPoints());
        intention.sold(moment, otherUser);
        assertEquals(5, intention.getOffered().getPoints());
    }

    @DisplayName("When an intention was solded, after 30 minutes, its add 5 points to its demander")
    @Test
    void testWhenAnIntentionWasSoledAfter30MinutesItAdd5PointsToItsDemander(){
        Timestamp moment = new Timestamp(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(31));
        assertEquals(0, otherUser.getPoints());
        intention.sold(moment, otherUser);
        assertEquals(5, otherUser.getPoints());
    }

    @DisplayName("Intention Return True When Belongs Between Two Dates")
    @Test
    void testIntentionReturnTrueWhenBelongsBetweenTwoDates() throws ParseException {
        String dateInitString = "01-01-2022";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date init = formatter.parse(dateInitString);
        Date end = new Timestamp(intention.getTimeStamp().getTime() + 1000);

        assertTrue(intention.isBetween(init, end));
    }

    @DisplayName("Status OFFERED can change to SOLD")
    @Test
    void testStatusOFFEREDCanChangeToSold(){
        Status status = Status.OFFERED;
        status = status.changeTo(Status.SOLD);

        assertEquals(Status.SOLD, status);
    }

    @DisplayName("Status OFFERED is allowed to change from SOLD")
    @Test
    void testStatusOFFEREDIsAllowedToChangeFromSold(){
        assertTrue(Status.SOLD.allowed(Status.OFFERED));
    }

    @DisplayName("Status CLOSED is not allowed to change from SOLD")
    @Test
    void testStatussNotAllowedToChangeFromSoldToClosed(){
        assertFalse(Status.SOLD.allowed(Status.CLOSED));
    }

   @DisplayName("Status WAITINGFORDELIVERY is not allowed to change from CLOSED")
    @Test
    void testStatussNotAllowedToChangeFromClosedToWaitingForDelivery(){
        assertFalse(Status.CLOSED.allowed(Status.WAITINGFORDELIVERY));
    }


    @DisplayName("When change from WAITINGFORTRANSFER to OFFERED a exception is thrown")
    @Test
    void testExcepThrownWhenChangingFromWFT2OFFERED(){
        StatusChangeErrorException exception = assertThrows(StatusChangeErrorException.class, () ->
                Status.WAITINGFORTRANSFER.changeTo(Status.OFFERED)
        );

        assertEquals(Status.OFFERED, exception.status);
    }

    @DisplayName("Intention Return False When donesnt Belong Between Two Dates")
    @Test
    void testIntentionReturnFalseWhenDoesntBelongBetweenTwoDates() throws ParseException {
        String dateInitString = "01-01-1922";
        String dateEndString = "02-01-1922";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date init = formatter.parse(dateInitString);
        Date end = formatter.parse(dateEndString);
        assertFalse(intention.isBetween(init, end));
    }

    @DisplayName("Intention Return False When init date is before end date")
    @Test
    void testIntentionReturnFalseWhenInitDateIsBeforeEndDate() throws ParseException {
        String dateInitString = "02-01-1922";
        String dateEndString = "01-01-1922";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date init = formatter.parse(dateInitString);
        Date end = formatter.parse(dateEndString);
        assertFalse(intention.isBetween(init, end));
    }
}
