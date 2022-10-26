package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Report Tests")
@SpringBootTest
class ReportTest {
    @DisplayName("A report exists")
    @Test
    void testReportExist(){
        Report report = new Report(CryptoName.AAVEUSDT, new BigDecimal("42"), 2, new BigDecimal("1"), new BigDecimal("154.10"));
        assertEquals(CryptoName.AAVEUSDT, report.getCrypto());
        assertEquals(new BigDecimal("42"), report.getTotalInDollars());
        assertEquals(new BigDecimal("1"), report.getCurrentPrice());
        assertEquals(2, report.getAmount());
        assertEquals(new BigDecimal("154.10"), report.getCurrentPriceInPesos());

    }
}
