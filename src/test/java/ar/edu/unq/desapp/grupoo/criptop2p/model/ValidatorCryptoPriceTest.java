package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("User Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ValidatorCryptoPriceTest {
    ValidatorCryptoPrice validator;
    @Mock
    User anUser;
    @BeforeEach
    void setUp(){
        validator = new ValidatorCryptoPrice();
    }

    @Test
    void createintentionWithStatusOFFEREDWhenTheCurrentPriceIsBeetweenTheRangeGivenBy5PerCentMoreOrLess(){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT, new BigDecimal(2.0));
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
    }

    @Test
    void createintentionWithStatusOFFEREDWhenTheCurrentPriceIsBeetweenTheRangeGivenBy5PerCentMore(){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT, new BigDecimal(2.1));
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
    }


    @Test
    void createintentionWithStatusOFFEREDWhenTheCurrentPriceIsBeetweenTheRangeGivenBy5PerCentLess(){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT, new BigDecimal(1.9));
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
    }

    @Test
    void createintentionCreateAnIntentionWithStatusCANCELEDBYSYSTEMWhenTheCurrentPriceIs5PerCentMore(){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT, new BigDecimal(1.8));
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }

    void createintentionCreateAnIntentionWithStatusCANCELEDBYSYSTEMWhenTheCurrentPriceIs5PerCentLess(){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT, new BigDecimal (2.2));
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }
}
