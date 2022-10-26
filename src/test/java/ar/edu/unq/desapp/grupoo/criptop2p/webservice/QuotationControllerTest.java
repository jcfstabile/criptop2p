package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.Quotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("Quotation Controller Tests")
@SpringBootTest
@Transactional
class QuotationControllerTest {

    @Autowired
    private QuotationController quotationController;

    @DisplayName("The QuotationController returns a list")
    @Test
    void testQuotationControllerReturnAListOf() {
        assertInstanceOf(List.class, quotationController.allQuotations());
    }

    @DisplayName("The QuotationController returns a list of quotations")
    @Test
    void testQuotationControllerReturnAListOfQuotations() {
        assertInstanceOf(Quotation.class, quotationController.allQuotations().get(0));
    }

    @DisplayName("All quotations returns the same cant of quatations that the app has cryptos name")
    @Test
    void testAllQuotationsGetsTheSameCountOfQuatantionThatCryptosNames(){
       assertEquals(Arrays.asList(CryptoName.values()).size(), quotationController.allQuotations().size());
    }
}
