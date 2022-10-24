package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;

@DisplayName("InspectUser tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class InspectUserTest {

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
    }

    @DisplayName("Inspect returns a list empty when there aren't intentions between init date and final date")
    @Test
    void testInspectAUserReturnsAnEmptyListWhenThereAreNotIntentionsBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(new ArrayList<>(0));
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertEquals(0, reports.size());
    }

    @DisplayName("Inspect returns a list with an only report when there is an only intention between init and final date")
    @Test
    void testInspectAUserReturnsAListWithOneReport(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerBuyATOMUSDT);
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertEquals(1, reports.size());
    }

    @DisplayName("Inspect returns a list of reports")
    @Test
    void testInspectAUserReturnsAListOfReports(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerSellATOMUSDT);
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertInstanceOf(Report.class, reports.get(0));
    }

    @DisplayName("InspectUser returns a list of one expected report when there is only intentention between these dates")
    @Test
    void testInspectAUserReturnsAListOfOneeExpectedReportWhenThereIsOnlyIntententionBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offerSellATOMUSDT);
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertInstanceOf(Report.class, reports.get(0));
    }

    @DisplayName("InspectUser returns a list of two expected reports when there are 2 intententions with different symbols between these dates")
    @Test
    void testInspectAUserReturnsAListOfTwoExpectedReportsWhenThereAre2IntententionsBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersSellATOMUSDTAndBuyBNBUSDT);
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertInstanceOf(Report.class, reports.get(0));
    }

    @DisplayName("InspectUser returns a list of one report when there are 2 intententions with same symbols but different type between these dates")
    @Test
    void testInspectAUserReturnsAListOfOneExpectedReportWhenThereAre2IntententionsWithSameSymbolsButDifferentTypeBetweenTheseDates(@Mock User anUser){
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offersBuyAndSellATOMUSDT);
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertInstanceOf(Report.class, reports.get(0));
    }

    @DisplayName("Inspect an user returns a List of two reports when there are 4 Intententions 2 same symbol x 2 Different types between these dates")
    @Test
    void testInspectAUserReturnsAListOfTwoReportsWhenThereAre4IntententionsWith2WithSameX2DifferentTypesBetweenTheseDates(@Mock User anUser){
        Intention intention3 = new Intention(anUser, 1, BigDecimal.valueOf(Long.parseLong("248")), new Sell(), CryptoName.BNBUSDT);
        offersBuyAndSellATOMUSDT.add(intention2);
        offersBuyAndSellATOMUSDT.add(intention3);
        List<Intention> offers = offersBuyAndSellATOMUSDT;
        Mockito.lenient().when(anUser.offersBetween(init, end)).thenReturn(offers);
        List<Report> reports = inspector.offersBetween(anUser, init, end);
        assertInstanceOf(Report.class, reports.get(0));
    }

}
