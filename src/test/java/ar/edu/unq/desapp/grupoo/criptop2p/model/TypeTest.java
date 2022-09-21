package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.IntentionBuilder;
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
    IntentionBuilder anyIntention = new IntentionBuilder();
    Intention anIntentionBUY;
    Intention anIntentionSELL;
    @BeforeEach
    void setUp(){
        anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        anIntentionSELL = anyIntention.withType(Type.SELL).withPrice(2).build();
        anIntentionBUY = anyIntention.withType(Type.BUY).withPrice(2).build();
    }

    @DisplayName("Type SELL give User's CVU as shippingAddress")
    @Test
    void testTypeSellReturnTheUsersCVU(){
        assertEquals(anUser.getCvu(), Type.SELL.shippingAddress(anUser));
    }

    @DisplayName("Type BUY give User's walletAddress as shippingAddress")
    @Test
    void testTypeBuyReturnTheUsersWalletAddress(){
        assertEquals(anUser.getWalletAddress(), Type.BUY.shippingAddress(anUser));
    }

    @DisplayName("When the current price is greater than the Intention's BUY, verify the acceptance of an Intention to CANCELEDBYSYSTEM")
    @Test
    void testToVerifyIfanIntentionIsAceptedCanceledTheIntentionWhenTheCurrentPriceisBiggerThanIntentionPriceAndtheTypeisBUY(){
        assertEquals(Status.OFFERED, anIntentionBUY.getStatus());
        Type.BUY.verifyIfIsAcepted(anUser, anIntentionBUY, new BigDecimal(3));
        assertEquals(Status.CANCELEDBYSYSTEM, anIntentionBUY.getStatus());
    }

    @DisplayName("When the current price is smaller than the Intention's, SELL verify the acceptance of an Intention to CANCELEDBYSYSTEM")
    @Test
    void testToVerifyIfanIntentionIsAceptedCanceledTheIntentionWhenTheCurrentPriceisSmallerThanIntentionPriceAndTheTypeIsSELL(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.SELL.verifyIfIsAcepted(anUser, anIntentionSELL, new BigDecimal(1));
        assertEquals(Status.CANCELEDBYSYSTEM, anIntentionSELL.getStatus());
    }

    @Test
    @DisplayName("When the current price is smaller than the Intention's, BUY verify the acceptance of an Intention to SOLD")
    void testToVerifyIfanIntentionModicateToSOLDTheIntentionWhenTheCurrentPriceisBiggerThanIntentionPriceAndtheTypeisSELL(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.BUY.verifyIfIsAcepted(anUser, anIntentionSELL, new BigDecimal(1));
        assertEquals(Status.SOLD, anIntentionSELL.getStatus());
    }

    @DisplayName("When the current price is smaller than the Intention's, SELL verify the acceptance of an Intention to SOLD")
    @Test
    void testToVerifyIfanIntentionModificateToSoldTheIntentionWhenTheCurrentPriceisSmallerThanIntentionPriceAndTheTypeIsSELLBUY(){
        assertEquals(Status.OFFERED, anIntentionBUY.getStatus());
        Type.SELL.verifyIfIsAcepted(anUser, anIntentionBUY, new BigDecimal(3));
        assertEquals(Status.SOLD, anIntentionBUY.getStatus());
    }

    @DisplayName("When the current price is same of the Intention's, SELL verify the acceptance of an Intention to SOLD")
    @Test
    void testToVerifyIfanIntentionIsAceptedModificateTheIntentionStatusToSOLDWhenTheCurrentPriceisEqualsToTheIntentionPriceAndTheTypeIsSELL(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.SELL.verifyIfIsAcepted(anUser, anIntentionSELL, new BigDecimal(2));
        assertEquals(Status.SOLD, anIntentionSELL.getStatus());
    }

    @DisplayName("When the current price is same of the Intention's, BUY verify the acceptance of an Intention to SOLD")
    @Test
    void testToVerifyIfanIntentionIsAceptedModificateToSoldTheIntentionStatusWhenTheCurrentPriceisEqualsToTheIntentionPriceAndTheTypeIsBUY(){
        assertEquals(Status.OFFERED, anIntentionSELL.getStatus());
        Type.SELL.verifyIfIsAcepted(anUser, anIntentionSELL, new BigDecimal(2));
        assertEquals(Status.SOLD, anIntentionSELL.getStatus());
    }
}
