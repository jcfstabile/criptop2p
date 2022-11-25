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

@DisplayName("InspectUser tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InspectUserTest {

    InspectUser inspector;
    Intention intention0;
    Intention intention1;
    Intention intention2;
    List<Intention> offerBuyATOMUSDT;
    List<Intention> offerSellATOMUSDT;
    List<Intention> offersBuyAndSellATOMUSDT;
    List<Intention> offersSellATOMUSDTAndBuyBNBUSDT;
    Date init;
    Date end;
    BigDecimal quotationUSD;

    Quoter aQuoter;

    @BeforeEach
    void setUp(@Mock User anUser){
        inspector = new InspectUser();
        intention0 = new Intention(anUser, 1, BigDecimal.valueOf(Double.parseDouble("1.48")), new Buy(), CryptoName.ATOMUSDT);
        intention1 = new Intention(anUser, 1, BigDecimal.valueOf(Double.parseDouble("1.48")), new Sell(), CryptoName.ATOMUSDT);
        intention2 = new Intention(anUser, 1, BigDecimal.valueOf(Double.parseDouble("248")), new Buy(), CryptoName.BNBUSDT);
        offerBuyATOMUSDT = new ArrayList<>(Collections.singletonList(intention0));
        offerSellATOMUSDT = new ArrayList<>(Collections.singletonList(intention1));
        offersBuyAndSellATOMUSDT = new ArrayList<>(Arrays.asList(intention0, intention1));
        offersSellATOMUSDTAndBuyBNBUSDT = new ArrayList<>(Arrays.asList(intention1, intention2));
        init = mock(Date.class);
        end = mock(Date.class);
        quotationUSD = BigDecimal.valueOf(Double.parseDouble("154.10"));
        aQuoter = mock(Quoter.class);
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(new BigDecimal("1"));
    }

    @DisplayName("InspectUser returns a formless")
    @Test
    void testInspectAUserReturnsAFormless(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersSellATOMUSDTAndBuyBNBUSDT);
        assertInstanceOf(FormDTO.class, inspector.offersBetween(anUser, init, end, aQuoter));
    }

    @DisplayName("Formless has a date")
    @Test
    void testInspectAUserReturnsAFormlessWithADate(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(new ArrayList<>(0));
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertInstanceOf(Date.class, formDTO.getDate());
    }

    @DisplayName("Formless has a list of reports")
    @Test
    void testInspectAUserReturnsAFormlessWithAListOfReports(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(new ArrayList<>(0));
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertInstanceOf(List.class, formDTO.getReports());
    }

    @DisplayName("Inspect returns a form with total in 0 USD when there are not offers between dates")
    @Test
    void testInspectAUserReturnsFormWith0USDWhenThrereAreNotOffersBetweenDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(new ArrayList<>(0));
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(BigDecimal.ZERO, formDTO.getTotalInDollars());
    }

    @DisplayName("Inspect returns a form with  correct total inUSD when there is an only intention between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInUSDWhenThereIsAnOnlyIntentionBetweenInitAndFinalDate(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerBuyATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("1.48"), formDTO.getTotalInDollars());
    }

    @DisplayName("Inspect returns a form with  correct total inUSD when there is two intentions, each different crypto, between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInUSDWhenThereAre2IntentionOfDifferentCryptoBetweenInitAndFinalDate(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersSellATOMUSDTAndBuyBNBUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("249.48"), formDTO.getTotalInDollars());
    }

    @DisplayName("Inspect returns a form with  correct total inUSD when there is two intentions of same crypto, between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInUSDWhenThereAre2IntentionOfSameCryptoBetweenInitAndFinalDate(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersBuyAndSellATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("2.96"), formDTO.getTotalInDollars());
    }

    @DisplayName("Inspect returns a form with correct total in USD when there are four intentions, each pair of different crypto, between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInUSDWhenThereAre4IntentionOfEachPairSameCryptoBetweenInitAndFinalDate(@Mock User anUser){
        Intention intention3 = new Intention(anUser, 1, BigDecimal.valueOf(Long.parseLong("248")), new Sell(), CryptoName.BNBUSDT);
        offersBuyAndSellATOMUSDT.add(intention2);
        offersBuyAndSellATOMUSDT.add(intention3);
        List<Intention> offers = offersBuyAndSellATOMUSDT;
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offers);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("498.96"), formDTO.getTotalInDollars());
    }

    @DisplayName("Inspect returns a form with total in $ 0 when there are not offers between dates")
    @Test
    void testInspectAUserReturnsFormWith0PesosWhenThrereAreNotOffersBetweenDates(@Mock User anUser, @Mock Quoter aQuoter){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(new ArrayList<>(0));
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(quotationUSD);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(0, formDTO.getTotalInPesos(aQuoter).compareTo(new BigDecimal(0)));
    }

    @DisplayName("Inspect returns a form with  correct total in $ when there is an only intention between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInPesosWhenThereIsAnOnlyIntentionBetweenInitAndFinalDate(@Mock User anUser, @Mock Quoter aQuoter){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerBuyATOMUSDT);
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(quotationUSD);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("228.068"), formDTO.getTotalInPesos(aQuoter));
    }

    @DisplayName("Inspect returns a form with  correct total in $ when there is two intentions, each different crypto, between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInPesosWhenThereAre2IntentionOfDifferentCryptoBetweenInitAndFinalDate(@Mock User anUser, @Mock Quoter aQuoter){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersSellATOMUSDTAndBuyBNBUSDT);
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(quotationUSD);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("38444.868"), formDTO.getTotalInPesos(aQuoter));
    }

    @DisplayName("Inspect returns a form with  correct total in $ when there is two intentions of same crypto, between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalIn$WhenThereAre2IntentionOfSameCryptoBetweenInitAndFinalDate(@Mock User anUser, @Mock Quoter aQuoter){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersBuyAndSellATOMUSDT);
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(quotationUSD);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("456.136"), formDTO.getTotalInPesos(aQuoter));
    }

    @DisplayName("Inspect returns a form with correct total in $ when there are four intentions, each pair of different crypto, between init and final date")
    @Test
    void testInspectAUserReturnsAFormWithCorrectTotalInPesosWhenThereAre4IntentionOfEachPairSameCryptoBetweenInitAndFinalDate(@Mock User anUser, @Mock Quoter aQuoter){
        Intention intention3 = new Intention(anUser, 1, BigDecimal.valueOf(Long.parseLong("248")), new Sell(), CryptoName.BNBUSDT);
        offersBuyAndSellATOMUSDT.add(intention2);
        offersBuyAndSellATOMUSDT.add(intention3);
        List<Intention> offers = offersBuyAndSellATOMUSDT;
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offers);
        Mockito.lenient().when(aQuoter.quotationOfUsd()).thenReturn(quotationUSD);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        assertEquals(new BigDecimal("76889.736"), formDTO.getTotalInPesos(aQuoter));
    }

    @DisplayName("Inspect returns a list with an only report when there is an only intention between init and final date")
    @Test
    void testInspectAUserReturnsAListWithOneReport(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerBuyATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertEquals(1, reportDTOS.size());
    }

    @DisplayName("Inspect returns a list of reports")
    @Test
    void testInspectAUserReturnsAListOfReports(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerSellATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
    }

    @DisplayName("InspectUser returns a list of one expected report when there is only intentention between these dates")
    @Test
    void testInspectAUserReturnsAListOfOneeExpectedReportWhenThereIsOnlyIntententionBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerSellATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
    }

    @DisplayName("InspectUser returns a list of two expected reports when there are 2 intententions with different symbols between these dates")
    @Test
    void testInspectAUserReturnsAListOfTwoExpectedReportsWhenThereAre2IntententionsBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersSellATOMUSDTAndBuyBNBUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
    }

    @DisplayName("InspectUser returns a list of one report when there are 2 intententions with same symbols but different type between these dates")
    @Test
    void testInspectAUserReturnsAListOfOneExpectedReportWhenThereAre2IntententionsWithSameSymbolsButDifferentTypeBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersBuyAndSellATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
    }

    @DisplayName("Inspect an user returns a List of two reports when there are 4 Intententions 2 same symbol x 2 Different types between these dates")
    @Test
    void testInspectAUserReturnsAListOfTwoReportsWhenThereAre4IntententionsWith2WithSameX2DifferentTypesBetweenTheseDates(@Mock User anUser){
        Intention intention3 = new Intention(anUser, 1, BigDecimal.valueOf(Long.parseLong("248")), new Sell(), CryptoName.BNBUSDT);
        offersBuyAndSellATOMUSDT.add(intention2);
        offersBuyAndSellATOMUSDT.add(intention3);
        List<Intention> offers = offersBuyAndSellATOMUSDT;
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offers);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
    }

    @DisplayName("Inspect returns a list with an only report when there is an only intention between init and final date and it is like expected")
    @Test
    void testInspectAUserReturnsAListWithOneReportAndItIsLikeExpected(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerBuyATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        ReportDTO reportDTO = reportDTOS.get(0);
        assertEquals(1, reportDTOS.size());
        assertEquals(1, reportDTO.getAmount());
        assertEquals(CryptoName.ATOMUSDT, reportDTO.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("1.48")), reportDTO.getTotalInDollars());
    }
    @DisplayName("Inspect returns a list of reports")
    @Test
    void testInspectAUserReturnsAListOfOneReportAndItIsLikeExpected(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerSellATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        ReportDTO reportDTO = reportDTOS.get(0);
        assertInstanceOf(ReportDTO.class, reportDTO);
        assertEquals(1, reportDTOS.size());
        assertEquals(1, reportDTO.getAmount());
        assertEquals(CryptoName.ATOMUSDT, reportDTO.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("1.48")), reportDTO.getTotalInDollars());
    }

    @DisplayName("InspectUser returns a list of two expected reports when there are 2 intententions with different symbols between these dates")
    @Test
    void testInspectAUserReturnsAListOfTwoExpectedReportsWhenThereAre2IntententionsBetweenTheseDatesAndTheyAreLikeExpected(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersSellATOMUSDTAndBuyBNBUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
        assertEquals(2, reportDTOS.size());

        List<ReportDTO> reportDTOATOMUSDTList = reportDTOS.stream().filter(report -> report.getCrypto().equals(CryptoName.ATOMUSDT)).toList();
        assertEquals(1, reportDTOATOMUSDTList.size());

        ReportDTO reportDTOATOMUSDT = reportDTOATOMUSDTList.get(0);

        assertInstanceOf(ReportDTO.class, reportDTOATOMUSDT);
        assertEquals(CryptoName.ATOMUSDT, reportDTOATOMUSDT.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("1.48")), reportDTOATOMUSDT.getTotalInDollars());

        List<ReportDTO> reportDTOBNBUSDTList = reportDTOS.stream().filter(report -> report.getCrypto().equals(CryptoName.BNBUSDT)).toList();
        assertEquals(1, reportDTOBNBUSDTList.size());
        ReportDTO reportDTOBNBUSDT = reportDTOBNBUSDTList.get(0);
        assertInstanceOf(ReportDTO.class, reportDTOBNBUSDT);
        assertEquals(CryptoName.BNBUSDT, reportDTOBNBUSDT.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("248")), reportDTOBNBUSDT.getTotalInDollars());
    }

    @DisplayName("InspectUser returns a list of one report when there are 2 intententions with same symbols but different type between these dates")
    @Test
    void testInspectAUserReturnsAListOfOneExpectedReportWhenThereAre2IntententionsWithSameSymbolsButDifferentTypeBetweenTheseDatesAndItIsLikeExpected(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersBuyAndSellATOMUSDT);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
        assertEquals(1, reportDTOS.size());

        List<ReportDTO> reportDTOATOMUSDTList = reportDTOS.stream().filter(report -> report.getCrypto().equals(CryptoName.ATOMUSDT)).toList();
        assertEquals(1, reportDTOATOMUSDTList.size());
        ReportDTO reportDTOATOMUSDT = reportDTOATOMUSDTList.get(0);

        assertInstanceOf(ReportDTO.class, reportDTOATOMUSDT);
        assertEquals(CryptoName.ATOMUSDT, reportDTOATOMUSDT.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("2.96")), reportDTOATOMUSDT.getTotalInDollars());
    }

    @DisplayName("Inspect an user returns a List of two reports when there are 4 Intententions 2 same symbol x 2 Different types between these dates")
    @Test
    void testInspectAUserReturnsAListOfTwoReportsWhenThereAre4IntententionsWith2WithSameX2DifferentTypesBetweenTheseDatesAndTheyAreLikeExpected(@Mock User anUser){
        Intention intention3 = new Intention(anUser, 1, BigDecimal.valueOf(Long.parseLong("248")), new Sell(), CryptoName.BNBUSDT);
        offersBuyAndSellATOMUSDT.add(intention2);
        offersBuyAndSellATOMUSDT.add(intention3);
        List<Intention> offers = offersBuyAndSellATOMUSDT;
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offers);
        FormDTO formDTO = inspector.offersBetween(anUser, init, end, aQuoter);
        List<ReportDTO> reportDTOS = formDTO.getReports();
        assertInstanceOf(ReportDTO.class, reportDTOS.get(0));
        assertEquals(2, reportDTOS.size());

        List<ReportDTO> reportDTOATOMUSDTList = reportDTOS.stream().filter(report -> report.getCrypto().equals(CryptoName.ATOMUSDT)).toList();
        assertEquals(1, reportDTOATOMUSDTList.size());
        ReportDTO reportDTOATOMUSDT = reportDTOATOMUSDTList.get(0);
        assertInstanceOf(ReportDTO.class, reportDTOATOMUSDT);
        assertEquals(CryptoName.ATOMUSDT, reportDTOATOMUSDT.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("2.96")), reportDTOATOMUSDT.getTotalInDollars());

        List<ReportDTO> reportDTOBNBUSDTList = reportDTOS.stream().filter(report -> report.getCrypto().equals(CryptoName.BNBUSDT)).toList();
        assertEquals(1, reportDTOBNBUSDTList.size());
        ReportDTO reportDTOBNBUSDT = reportDTOBNBUSDTList.get(0);
        assertInstanceOf(ReportDTO.class, reportDTOBNBUSDT);
        assertEquals(CryptoName.BNBUSDT, reportDTOBNBUSDT.getCrypto());
        assertEquals(BigDecimal.valueOf(Double.parseDouble("496")), reportDTOBNBUSDT.getTotalInDollars());
    }
}

