package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TyṕeIntentionDeliveryTest {
    TypeIntentionDelivery typeDelivery;
    @BeforeEach
    void setUp(){
        typeDelivery = new TypeIntentionDelivery();
    }


    @DisplayName("When convertToDatabaseColumn receive receiveAnIntention Of Buy Type return a Status.SELL")
    @Test
    void testConvertToDatabaseColumnReturnsAStatusSellWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Sell.class, typeDelivery.get("SELL"));
    }

    @DisplayName("When convertToDatabaseColumn receive receiveAnIntention Of Buy Type return a Status.BUY")
    @Test
    void testConvertToDatabaseColumnReturnsAStatusBuyWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Buy.class, typeDelivery.get("BUY"));
    }
}
