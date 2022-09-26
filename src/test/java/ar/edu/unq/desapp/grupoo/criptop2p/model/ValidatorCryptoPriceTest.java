package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("ValidatorCriptoPrice Tests")
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


    @DisplayName("A Validator can change its range of validation")
    @Test
    void testValidatorCanChangeItsPercent(){
        assertEquals(new BigDecimal(5), validator.getPercent());
        validator.setPerCent(new BigDecimal(4.20));
        assertEquals(new BigDecimal(4.20), validator.getPercent());

    }

    @DisplayName("When a Validator is instantiated have a 5% range")
    @Test
    void testThePercentOfValidatorBeginIn5(){
        assertEquals(new BigDecimal(5),validator.getPercent());
    }

    @DisplayName("@ When a Intention is created with a price in range get a status of OFFERED")
    @ParameterizedTest
    @ValueSource(doubles = {2.0, 2.1, 1.9})
    void createintentionWithStatusOFFEREDWhenTheCurrentPriceIsBeetweenTheRangeGivenBy5PerCentMoreOrLess(double currentPrice){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), new Sell(), CryptoName.ATOMUSDT, new BigDecimal(currentPrice));

        assertEquals(anUser, intention.getOffered());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertEquals(new Sell().getName(), intention.getType().getName());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.OFFERED, intention.getStatus());
    }


    @DisplayName("@ When a Intention is created with a price out of range get a status of CANCELEDBYSYSTEM")
    @ParameterizedTest
    @ValueSource(doubles = {2.2, 1.8})
    void createintentionCreateAnIntentionWithStatusCANCELEDBYSYSTEMWhenTheCurrentPriceIs5PerCentOutOfRange(double currentPrice){
        Intention intention = validator.createIntention(anUser, 1, new BigDecimal(2), new Sell(), CryptoName.ATOMUSDT, new BigDecimal(currentPrice));

        assertEquals(anUser, intention.getOffered());
        assertEquals(1, intention.getCount());
        assertEquals(new BigDecimal(2), intention.getPrice());
        assertInstanceOf(Sell.class, intention.getType());
        assertEquals(CryptoName.ATOMUSDT, intention.getCrypto());
        assertEquals(Status.CANCELEDBYSYSTEM, intention.getStatus());
    }

}
