package ar.edu.unq.desapp.grupoo.criptop2p.model;


import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Intention Tests")
@SpringBootTest
public class IntentionTest {
    User anUser;
    Intention intention;
    Intention intentionBuy;
    Intention intentionSell;
    @BeforeEach
    void setUp() {
        anUser = new User("Jim", "Ken", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
        intention = new Intention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT);
        intentionBuy = new Intention(anUser, 1, new BigDecimal(2), Type.BUY, CryptoName.ATOMUSDT);
        intentionSell = new Intention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT);

    }
    @Test
    void testAnIntentionExist(){
        assertEquals(anUser, intention.getOffered());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
        assertNull(intention.getDemander());
    }
    @Test
    void testAnIntentionDTOExist(){
        IntentionDTO intentionDTO = new IntentionDTO(1, new BigDecimal(2),Type.SELL, CryptoName.ATOMUSDT);
        assertEquals(1, intentionDTO.getCount());
        assertEquals(new BigDecimal(2), intentionDTO.getPrice());
        assertEquals(Type.SELL, intentionDTO.getType());
        assertEquals(CryptoName.ATOMUSDT, intentionDTO.getCryptoName());
    }
    @Test
    void testAnIntentionCanChangeStatusToCanceled(){
        assertEquals(Status.OFFERED, intention.getStatus());
        intention.canceled();
        assertEquals(Status.CANCELED, intention.getStatus());
    }
    @Test
    void testAnIntentionCanChangeStatusToCanceledBySystem(){
        assertEquals(Status.OFFERED, intention.getStatus());
        intention.canceledBySystem();
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }
    @Test
    void testIsBiggerThanReturnTrueWhenTheCurrentPriceIsBiggerThanPrice(){
        assertTrue(intention.isBiggerThan(new BigDecimal(3)));
    }
    @Test
    void testIsSmallerThanReturnTrueWhenTheCurrentPriceIsSmallerThanPrice(){
        assertTrue(intention.isSmallerThan(new BigDecimal(1)));
    }
    @Test
    void testIsBiggerThanReturnFalseWhenTheCurrentPriceIsNotBiggerThanPrice(){
        assertFalse(intention.isBiggerThan(new BigDecimal(1.9)));
    }
    @Test
    void testIsSmallerThanReturnFalseWhenTheCurrentPriceIsNotSmallerThanPrice(){
        assertFalse(intention.isSmallerThan(new BigDecimal(2.1)));
    }
    @Test
    void testIsBiggerThanReturnFalseWhenTheCurrentPriceIsEqualToPrice(){
        assertFalse(intention.isBiggerThan(new BigDecimal(2)));
    }
    @Test
    void testIsSmallerThanReturnFalseWhenTheCurrentPriceIsEqualToPrice(){
        assertFalse(intention.isSmallerThan(new BigDecimal(2)));
    }
    @Test
    void testthePriceOfAnIntentionBuyDoesNotChangeWhenTheCurrentPriceAndThePriceAreEquals(){
        BigDecimal before = intentionBuy.getPrice();
        intentionBuy.verifyIfIsAcepted(new BigDecimal(2));
        BigDecimal after = intentionBuy.getPrice();;
        assertEquals(before, after);
    }
    @Test
    void testthePriceOfAnIntentionSellDoesNotChangeWhenTheCurrentPriceAndThePriceAreEquals(){
        BigDecimal before = intentionSell.getPrice();
        intentionBuy.verifyIfIsAcepted(new BigDecimal(2));
        BigDecimal after = intentionSell.getPrice();;
        assertEquals(before, after);
    }
    @Test
    void testthePriceOfAnIntentionBuyChangeWhenTheCurrentPriceIsSmaller(){
        BigDecimal before = intentionBuy.getPrice();;
        assertEquals(new BigDecimal(2), before);
        intentionBuy.verifyIfIsAcepted(new BigDecimal(1));
        BigDecimal after = intentionBuy.getPrice();;
        assertEquals(new BigDecimal(1), after);
    }
    @Test
    void testthePriceOfAnIntentionSellDoesNotChangeWhenTheCurrentPriceAndThePriceIsBigger(){
        BigDecimal before = intentionSell.getPrice();;
        assertEquals(new BigDecimal(2), before);
        intentionSell.verifyIfIsAcepted(new BigDecimal(3));
        BigDecimal after = intentionSell.getPrice();;
        assertEquals(new BigDecimal(3), after);
    }

    @Test
    void testAnIntentionCanIdentificateIfAnUserIsItsOfferer(){
        Intention anIntention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.MATICUSDT, new BigDecimal(2));
        assertTrue(anIntention.isItsOfferer(anUser));
    }

    @Test
    void testAnIntentionCanIdentificateIfAnUserIsNotItsOfferer(){
        User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        Intention anIntention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.MATICUSDT, new BigDecimal(2));
        assertFalse(anIntention.isItsOfferer(otherUser));
    }

    @Test
    void testAnIntentionCanIdentificateIfAnUserIsItsDemander(){
        Intention anIntention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.MATICUSDT, new BigDecimal(2));
        User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        otherUser.accept(anIntention, new BigDecimal(2));
        assertTrue(anIntention.isItsDemander(otherUser));
    }

    @Test
    void testAnIntentionCanIdentificateIfAnUserIsNotItsDemander(){
        Intention anIntention = anUser.offer(1, new BigDecimal(2), Type.SELL, CryptoName.MATICUSDT, new BigDecimal(2));
        User otherUser = new User("Joe", "Kun", "asd@there.dom", "1234567891", "Pepito13!", "12345679", "1234567890123456789012");
        otherUser.accept(anIntention, new BigDecimal(2));
        assertFalse(anIntention.isItsDemander(anUser));
    }
}
