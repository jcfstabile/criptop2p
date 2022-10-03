package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TypeIntentionDeliveryTest {
    TypeIntentionDelivery typeDelivery;
    @BeforeEach
    void setUp(){
        typeDelivery = new TypeIntentionDelivery();
    }


    @DisplayName("When get of TypeDelivery receive 'SELL' return a instance of Sell class")
    @Test
    void testGetReturnsAStatusSellWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Sell.class, typeDelivery.get("SELL"));
    }

    @DisplayName("When get of TypeDelivery receive 'BUY' return a instance of Buy class")
    @Test
    void testGetReturnsAStatusBuyWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Buy.class, typeDelivery.get("BUY"));
    }
}
