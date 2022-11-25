package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quotation Test")
@SpringBootTest
class QuotationTest {

    @DisplayName("Quotation has price and cryptoName")
    @Test
    void checkDataClass(){
        Quotation quotation = new Quotation(CryptoName.ALICEUSDT, BigDecimal.valueOf(1.2));

        assertEquals(CryptoName.ALICEUSDT, quotation.getCryptoName());
        assertEquals(BigDecimal.valueOf(1.2), quotation.getPrice());
    }
}