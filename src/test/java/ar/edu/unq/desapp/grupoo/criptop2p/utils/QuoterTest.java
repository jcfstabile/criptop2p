package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quoter tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class QuoterTest {
    Quoter quoter;

    @BeforeEach
    void setUp(){
        quoter = new Quoter();
    }

    @DisplayName("Quoter returns the price of one dollar")
    @Test
    void testQuoterReturnADollarQuotation(){
        assertNotNull(quoter.quotationOfUsd());
    }

    @DisplayName("Quoter returns the price of one dollar")
    @Test
    void testQuoterReturnADollarQuotationLikeIsExpected(){
        assertTrue(quoter.quotationOfUsd().compareTo(BigDecimal.ZERO) > 0);
    }

    @DisplayName("Quoter returns the price of one dollar")
    @Test
    void testQuoterReturnABigDecimalLikeQuotation(){
        assertInstanceOf(BigDecimal.class, quoter.quotationOfUsd());
    }
}
