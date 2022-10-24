package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TypeIntention tests")
@SpringBootTest
class TypeIntentionConverterTest {
    TypeIntentionConverter typeConverter;
    @BeforeEach
    void setUp(){
        typeConverter = new TypeIntentionConverter();
    }


    @DisplayName("When convertToDatabaseColumn receive receiveAnIntention Of Buy Type return a Status.SELL")
    @Test
    void testConvertToDatabaseColumnReturnsAStatusSellWhenReceiveAnIntentionOfThisType(){
        assertEquals(TypeName.SELL.name(), typeConverter.convertToDatabaseColumn(new Sell()));
    }

    @DisplayName("When convertToDatabaseColumn receive receiveAnIntention Of Buy Type return a Status.BUY")
    @Test
    void testConvertToDatabaseColumnReturnsAStatusBuyWhenReceiveAnIntentionOfThisType(){
        assertEquals(TypeName.BUY.name(), typeConverter.convertToDatabaseColumn(new Buy()));
    }


    @DisplayName("When ConvertToEntity receive a Status.SELL returns an intance of Sell")
    @Test
    void testConvertToconvertToEntityAttributeReturnsAnIntanceOfSellWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Sell.class, typeConverter.convertToEntityAttribute("SELL"));
    }

    @DisplayName("When ConvertToEntity receive a Status.SELL returns an intance of Buy")
    @Test
    void testConvertToEntityAttributeReturnsAStatusBuyWhenReceiveAnIntentionOfThisType(){
        assertInstanceOf(Buy.class, typeConverter.convertToEntityAttribute("BUY"));
    }
}
