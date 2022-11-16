package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;
import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.Form;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.Report;
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
class FormTest {

    List<Report> emptyReports;
    List<Report> aListOfAReport;
    List<Report> aListOfTwoReports;
    BigDecimal quotationUSD;

    Quoter aQuoter;

    @BeforeEach
    void setUp(){
        quotationUSD = BigDecimal.valueOf(Double.parseDouble("154.10"));
        emptyReports = new ArrayList<>(0);
        Report report0 = new Report(CryptoName.ATOMUSDT, new BigDecimal("42.42"), 2, new BigDecimal("42"), new BigDecimal("1000"));
        Report report1 = new Report(CryptoName.BNBUSDT, new BigDecimal("442.42"), 3, new BigDecimal("42"), new BigDecimal("1000"));
        aListOfAReport = new ArrayList<>(List.of(report0));
        aListOfTwoReports = new ArrayList<>(Arrays.asList(report0, report1));
        aQuoter = mock(Quoter.class);

    }


    @DisplayName("Form has a date")
    @Test
    void testFormHasADate(){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
        Form form = new Form(emptyReports, aQuoter);
        assertInstanceOf(Date.class, form.getDate());
        assertNotNull(form.getDate());
    }

    @DisplayName("Form has a list of reports")
    @Test
    void testFormHasAListOfReports(){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
        Form form = new Form(emptyReports, aQuoter);
        assertInstanceOf(List.class, form.getReports());
        assertNotNull(form.getReports());
    }

    @DisplayName("Form hasnt any report")
    @Test
    void testFormHasNotAnyReportWhenGivenAEmptyList(){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
        Form form = new Form(emptyReports, aQuoter);
        List<Report> reports = form.getReports();
        assertEquals(0, reports.size());
    }

    @DisplayName("Form has one report and expected state")
    @Test
    void testFormHasOneExpectStateWhenGivenAListWithAReport(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        Form form = new Form(aListOfAReport, aQuoter);
        List<Report> reports = form.getReports();
        assertEquals(new BigDecimal("50.904"), form.getTotalInPesos(aQuoter));
        assertEquals(new BigDecimal("42.42"), form.getTotalInDollars());
        assertEquals(1, reports.size());
    }

    @DisplayName("Form has two reports and expected state")
    @Test
    void testFormHasAnExpectStateWhenGivenAListWithTwoReport(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        Form form = new Form(aListOfTwoReports, aQuoter);
        List<Report> reports = form.getReports();
        assertEquals(new BigDecimal("581.808"), form.getTotalInPesos(aQuoter));
        assertEquals(new BigDecimal("484.84"), form.getTotalInDollars());
        assertEquals(2, reports.size());
    }

    @DisplayName("Form has the total in dollars saved into var totalInDollar")
    @Test
    void testFormHasTheTotalInDollarsSavedINtoVarTotalInDollars(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        Form form = new Form(aListOfTwoReports, aQuoter);
        assertEquals(new BigDecimal("484.84"), form.getTotalDollars());
    }


    @DisplayName("Form has the total in pesos saved into var totalInPesos")
    @Test
    void testFormHasTheTotalInDollarsSavedINtoVarTotalInPesos(@Mock Quoter aQuoter){
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1.2"));
        Form form = new Form(aListOfTwoReports, aQuoter);
        assertEquals(new BigDecimal("581.808"), form.getTotalPesos());
    }
}
