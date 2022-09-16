package ar.edu.unq.desapp.grupoo.criptop2p.model;


import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
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
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
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
}
