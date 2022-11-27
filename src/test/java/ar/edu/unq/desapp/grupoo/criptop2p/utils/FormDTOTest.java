package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;
import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.FormDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.ReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("Formless Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FormDTOTest {

    List<ReportDTO> emptyReportDTOS;
    List<ReportDTO> aListOfAReportDTO;
    List<ReportDTO> aListOfTwoReportDTOS;
    BigDecimal quotationUSD;

    Quoter aQuoter;

    @BeforeEach
    void setUp(){
        quotationUSD = BigDecimal.valueOf(Double.parseDouble("154.10"));
        emptyReportDTOS = new ArrayList<>(0);
        ReportDTO reportDTO0 = new ReportDTO(CryptoName.ATOMUSDT, new BigDecimal("42.42"), 2, new BigDecimal("42"), new BigDecimal("1000"));
        ReportDTO reportDTO1 = new ReportDTO(CryptoName.BNBUSDT, new BigDecimal("442.42"), 3, new BigDecimal("42"), new BigDecimal("1000"));
        aListOfAReportDTO = new ArrayList<>(List.of(reportDTO0));
        aListOfTwoReportDTOS = new ArrayList<>(Arrays.asList(reportDTO0, reportDTO1));
        aQuoter = mock(Quoter.class);

    }


    @DisplayName("Form has a date")
    @Test
    void testFormHasADate(){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
        FormDTO formDTO = new FormDTO(emptyReportDTOS, aQuoter);
        assertInstanceOf(Date.class, formDTO.getDate());
        assertNotNull(formDTO.getDate());
    }

    @DisplayName("Form has a list of reports")
    @Test
    void testFormHasAListOfReports(){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
        FormDTO formDTO = new FormDTO(emptyReportDTOS, aQuoter);
        assertInstanceOf(List.class, formDTO.getReports());
        assertNotNull(formDTO.getReports());
    }

    @DisplayName("Form hasnt any report")
    @Test
    void testFormHasNotAnyReportWhenGivenAEmptyList(){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
        FormDTO formDTO = new FormDTO(emptyReportDTOS, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertEquals(0, reportDTOS.size());
    }

    @DisplayName("Form has one report and expected state")
    @Test
    void testFormHasOneExpectStateWhenGivenAListWithAReport(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        FormDTO formDTO = new FormDTO(aListOfAReportDTO, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertEquals(new BigDecimal("50.904"), formDTO.getTotalInPesos(aQuoter));
        assertEquals(new BigDecimal("42.42"), formDTO.getTotalInDollars());
        assertEquals(1, reportDTOS.size());
    }

    @DisplayName("Form has two reports and expected state")
    @Test
    void testFormHasAnExpectStateWhenGivenAListWithTwoReport(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        FormDTO formDTO = new FormDTO(aListOfTwoReportDTOS, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertEquals(new BigDecimal("581.808"), formDTO.getTotalInPesos(aQuoter));
        assertEquals(new BigDecimal("484.84"), formDTO.getTotalInDollars());
        assertEquals(2, reportDTOS.size());
    }

    @DisplayName("Form has the total in dollars saved into var totalInDollar")
    @Test
    void testFormHasTheTotalInDollarsSavedINtoVarTotalInDollars(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        FormDTO formDTO = new FormDTO(aListOfTwoReportDTOS, aQuoter);
        assertEquals(new BigDecimal("484.84"), formDTO.getTotalDollars());
    }


    @DisplayName("Form has the total in pesos saved into var totalInPesos")
    @Test
    void testFormHasTheTotalInDollarsSavedINtoVarTotalInPesos(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        FormDTO formDTO = new FormDTO(aListOfTwoReportDTOS, aQuoter);
        assertEquals(new BigDecimal("581.808"), formDTO.getTotalPesos());
    }
}
