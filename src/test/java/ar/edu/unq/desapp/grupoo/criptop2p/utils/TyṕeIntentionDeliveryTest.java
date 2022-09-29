package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
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


    @DisplayName("When get of TypeDelivery receive Sell return a instance of Sell class")
    @Test
    void testGetReturnsAStatusSellWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Sell.class, typeDelivery.get("SELL"));
    }

    @DisplayName("When get of TypeDelivery receive Buy return a instance of Buy class")
    @Test
    void testGetReturnsAStatusBuyWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Buy.class, typeDelivery.get("BUY"));
    }

    @DisplayName("Remove Type delete Instance of Sell when receive this instance")
    @Test
    void testRemoveTypeDeleteSell(){
        assertEquals(2, typeDelivery.types.size());
        typeDelivery.removeType(new Sell());
        assertEquals(1, typeDelivery.types.size());

    }

    @DisplayName("Remove Type delete Instance of Buy when receive this instance")
    @Test
    void testRemoveTypeDeleteBuy(){
        assertEquals(2, typeDelivery.types.size());
        typeDelivery.removeType(new Buy());
        assertEquals(1, typeDelivery.types.size());
    }

}
