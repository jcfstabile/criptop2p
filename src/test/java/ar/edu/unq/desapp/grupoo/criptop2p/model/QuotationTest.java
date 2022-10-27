package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Quotation Tests")
@SpringBootTest
@Transactional
class QuotationTest {
    QuotationDTO quotationSUT;

    @BeforeEach
    void setUp(){
        quotationSUT = new QuotationDTO(CryptoName.BNBUSDT.name(),"123");
    }

    @DisplayName("The price of Quotation is the expected")
    @Test
    void testNameOfAQuotationIsTheExpected() {
        assertEquals("BNBUSDT", quotationSUT.getCryptoName());
    }


    @DisplayName("The Name of Crypto of Quotation is the expected")
    @Test
    void testPriceOfAQuotationIsTheExpected() {
        assertEquals("123", quotationSUT.getPrice());
    }
}
