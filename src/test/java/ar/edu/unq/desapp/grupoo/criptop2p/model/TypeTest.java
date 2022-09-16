package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Type Tests")
@SpringBootTest
public class TypeTest {
    User anUser;
    Intention anIntentionBUY;
    Intention anIntentionSELL;
    @BeforeEach
    void setUp(){
        anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        anIntentionBUY = new Intention(anUser, 1, new BigDecimal(2), Type.BUY, CryptoName.TRXUSDT);
        anIntentionSELL = new Intention(anUser, 1, new BigDecimal(2), Type.SELL, CryptoName.NEOUSDT);
    }

    @Test
    void testTypeShellReturnTheUsersCVU(){
        assertEquals(anUser.getCvu(), Type.SELL.shippingAddress(anUser));
    }

    @Test
    void testTypeBuyReturnTheUsersCVU(){
        assertEquals(anUser.getWalletAddress(), Type.BUY.shippingAddress(anUser));
    }

    @Test
    void testToVerifyIfanIntentionIsAceptedCanceledTheIntentionWhenTheCurrentPriceisBiggerThanIntentionPriceAndtheTypeisBUY(){
        assertEquals(Status.OFFERED, anIntentionBUY.getStatus());
        Type.BUY.verifyIfIsAcepted(anIntentionBUY, new BigDecimal(3));
        assertEquals(Status.CANCELEDBYSYSTEM, anIntentionBUY.getStatus());
    }
    @Test
    void testToVerifyIfanIntentionIsAceptedCanceledTheIntentionWhenTheCurrentPriceisSmallerThanIntentionPriceAndTheTypeIsSELL(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.SELL.verifyIfIsAcepted(anIntentionSELL, new BigDecimal(1));
        assertEquals(Status.CANCELEDBYSYSTEM, anIntentionSELL.getStatus());
    }
    @Test
    void testToVerifyIfanIntentionModicateToSOLDTheIntentionWhenTheCurrentPriceisBiggerThanIntentionPriceAndtheTypeisSELL(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.BUY.verifyIfIsAcepted(anIntentionSELL, new BigDecimal(1));
        assertEquals(Status.SOLD, anIntentionSELL.getStatus());
    }
    @Test
    void testToVerifyIfanIntentionModificateToSoldTheIntentionWhenTheCurrentPriceisSmallerThanIntentionPriceAndTheTypeIsSELLBUY(){
        assertEquals(Status.OFFERED, anIntentionBUY.getStatus());
        Type.SELL.verifyIfIsAcepted(anIntentionBUY, new BigDecimal(3));
        assertEquals(Status.SOLD, anIntentionBUY.getStatus());
    }
    @Test
    void testToVerifyIfanIntentionIsAceptedModificateTheIntentionStatusToSOLDWhenTheCurrentPriceisEqualsToTheIntentionPriceAndTheTypeIsSELL(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.SELL.verifyIfIsAcepted(anIntentionSELL, new BigDecimal(2));
        assertEquals(Status.SOLD, anIntentionSELL.getStatus());
    }
    @Test
    void testToVerifyIfanIntentionIsAceptedModificateToSoldTheIntentionStatusWhenTheCurrentPriceisEqualsToTheIntentionPriceAndTheTypeIsBUY(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.SELL.verifyIfIsAcepted(anIntentionSELL, new BigDecimal(2));
        assertEquals(Status.SOLD, anIntentionSELL.getStatus());
    }
}
