package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.ReportDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Report Tests")
@SpringBootTest
class ReportDTOTest {
    @DisplayName("A report exists")
    @Test
    void testReportExist(){
        ReportDTO reportDTO = new ReportDTO(CryptoName.AAVEUSDT, new BigDecimal("42"), 2, new BigDecimal("1"), new BigDecimal("154.10"));
        assertEquals(CryptoName.AAVEUSDT, reportDTO.getCrypto());
        assertEquals(new BigDecimal("42"), reportDTO.getTotalInDollars());
        assertEquals(new BigDecimal("1"), reportDTO.getCurrentPrice());
        assertEquals(2, reportDTO.getAmount());
        assertEquals(new BigDecimal("154.10"), reportDTO.getCurrentPriceInPesos());

    }
}
