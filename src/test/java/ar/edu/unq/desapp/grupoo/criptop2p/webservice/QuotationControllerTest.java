package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("Quotation Controller Tests")
@SpringBootTest
@Transactional
public class QuotationControllerTest {

    @Autowired
    private QuotationController quotationController;

    /*
    @DisplayName("The QuotationController returns a list")
    @Test
    void testQuotationControllerReturnAListOf() {
        assertInstanceOf(List.class, quotationController.allQuotations());
    }

    @DisplayName("The QuotationController returns a list of quotations")
    @Test
    void testQuotationControllerReturnAListOfQuotations() {
        assertInstanceOf(Quotation.class, quotationController.allQuotations().get(0));
    }*/

}
