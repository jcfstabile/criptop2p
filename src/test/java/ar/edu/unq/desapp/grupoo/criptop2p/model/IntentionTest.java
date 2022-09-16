package ar.edu.unq.desapp.grupoo.criptop2p.model;


import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Intention Tests")
@SpringBootTest
public class IntentionTest {
    User anUser;
    Intention intention;
    @BeforeEach
    void setUp() {
        anUser = new User("Jim", "Ken", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
        intention = new Intention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.ATOMUSDT);
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
}
