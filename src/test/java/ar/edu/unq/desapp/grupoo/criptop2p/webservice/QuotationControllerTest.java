package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.CryptoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quotation Controller Tests")
@SpringBootTest
@Transactional
class QuotationControllerTest {

    @Autowired
    private QuotationController quotationController;

    @DisplayName("The QuotationController returns a list")
    @Test
    void testQuotationControllerReturnAListOf() throws InterruptedException {
        assertInstanceOf(List.class, quotationController.allQuotations());
    }

    @DisplayName("The QuotationController returns a list of quotations")
    @Test
    void testQuotationControllerReturnAListOfQuotations() throws InterruptedException {
        assertInstanceOf(QuotationDTO.class, quotationController.allQuotations().get(0));
    }

    @DisplayName("All quotations returns the same count of quotations that the app has cryptos name")
    @Test
    void testAllQuotationsGetsTheSameCountOfQuatantionThatCryptosNames() throws InterruptedException {
       assertEquals(Arrays.asList(CryptoName.values()).size(), quotationController.allQuotations().size());
    }

    @DisplayName("When asked for last 24hs quotations a timed quotation list is returned")
    @Test
    void testLast24hsForACrypto(){
        List<TimedQuotationDTO> last24hs = quotationController.last24hs("ATOMUSDT").getBody();

        assertNotNull(last24hs);
        assertInstanceOf(ArrayList.class, last24hs );
    }

    @DisplayName("When asked for last 24hs quotations of not existent crypto a exception is throw")
    @Test
    void testLast24hsForACryptoNotFound(){
        Exception exception = assertThrows(CryptoNotFoundException.class, () ->
                quotationController.last24hs("DONOTUSDT")
        );

        assertEquals("The crypto with name DONOTUSDT was not found", exception.getMessage());
    }
}