package ar.edu.unq.desapp.grupoo.criptop2p.model;


import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.IntentionBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.sql.Timestamp;
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

    @DisplayName("An IntentionDTO exist")
    @Test
    void testAnIntentionDTOExist() {
        IntentionDTO intentionDTO = new IntentionDTO(1, new BigDecimal(2), new Sell(), CryptoName.ATOMUSDT);
        assertEquals(1, intentionDTO.getCount());
        assertEquals(new BigDecimal(2), intentionDTO.getPrice());
        assertEquals(new Sell().getName(), intentionDTO.getType().getName());
        assertEquals(CryptoName.ATOMUSDT, intentionDTO.getCryptoName());
    }

    @DisplayName("An Intention can change Status to CANCELED")
    @Test
    void testAnIntentionCanChangeStatusToCanceled() {
        assertEquals(Status.OFFERED, intention.getStatus());
        intention.canceled();
        assertEquals(Status.CANCELED, intention.getStatus());
    }

    @DisplayName("An Intention can change Status to CANCELEDBYSYSTEM")
    @Test
    void testAnIntentionCanChangeStatusToCanceledBySystem() {
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
        intentionBuy.verifyIfIsAcepted(anUser, new BigDecimal(2));
        BigDecimal after = intentionBuy.getPrice();

        assertEquals(before, after);
    }

    @DisplayName("When the current price and the SELL Intention price are equals its do not change")
    @Test
    void testthePriceOfAnIntentionSellDoesNotChangeWhenTheCurrentPriceAndThePriceAreEquals() {
        BigDecimal before = intentionSell.getPrice();
        intentionSell.verifyIfIsAcepted(anUser, new BigDecimal(2));
        BigDecimal after = intentionSell.getPrice();

        assertEquals(before, after);
    }

    @DisplayName("When the current price is smaller than BUY Intention's price its change")
    @Test
    void testthePriceOfAnIntentionBuyChangeWhenTheCurrentPriceIsSmaller() {
        BigDecimal before = intentionBuy.getPrice();

        assertEquals(new BigDecimal(2), before);
        intentionBuy.verifyIfIsAcepted(anUser, new BigDecimal(1));
        BigDecimal after = intentionBuy.getPrice();

        assertEquals(new BigDecimal(1), after);
    }

    @DisplayName("When the SELL Intention's price is bigger than current price do not change")
    @Test
    void testthePriceOfAnIntentionSellDoesNotChangeWhenTheCurrentPriceAndThePriceIsBigger() {
        BigDecimal before = intentionSell.getPrice();

        assertEquals(new BigDecimal(2), before);
        intentionSell.verifyIfIsAcepted(anUser, new BigDecimal(3));
        BigDecimal after = intentionSell.getPrice();

        assertEquals(new BigDecimal(3), after);
    }

    @DisplayName("An Intention can identificate if an User is its offerer")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsItsOfferer() {
        Intention anIntention = anUser.offer(1, new BigDecimal(2), new Sell(), CryptoName.MATICUSDT, new BigDecimal(2));
        assertTrue(anIntention.isItsOfferer(anUser));
    }

    @DisplayName("An Intention can identificate if an User is not its offerer")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsNotItsOfferer() {
        Intention anIntention = anUser.offer(1, new BigDecimal(2), new Sell(), CryptoName.MATICUSDT, new BigDecimal(2));
        assertFalse(anIntention.isItsOfferer(otherUser));
    }

    @DisplayName("An Intention can identificate if an User is its demander")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsItsDemander() {
        Intention anIntention = anUser.offer(1, new BigDecimal(2), new Sell(), CryptoName.MATICUSDT, new BigDecimal(2));
        otherUser.accept(anIntention, new BigDecimal(2));
    }

    @DisplayName("An Intention can identificate if an User is not its demander")
    @Test
    void testAnIntentionCanIdentificateIfAnUserIsNotItsDemander() {
        Intention anIntention = anUser.offer(1, new BigDecimal(2), new Sell(), CryptoName.MATICUSDT, new BigDecimal(2));
        otherUser.accept(anIntention, new BigDecimal(2));
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
        assertEquals(Status.OFFERED, intention.getStatus());

        intention.waitingForTransfer();

        assertEquals(Status.WAITINGFORTRANSFER, intention.getStatus());
    }


    @DisplayName("An intention change its status to waiting for Delivery")
    @Test
    void testAnIntentionChangeItsStatusToWaitingForDeliveryToReceiveThisMessage(){
        assertEquals(Status.OFFERED, intention.getStatus());

        intention.waitingForDelivery();

        assertEquals(Status.WAITINGFORDELIVERY, intention.getStatus());
    }
}