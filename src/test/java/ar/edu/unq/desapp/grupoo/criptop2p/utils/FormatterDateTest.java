package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FormatterDate Tests")
@SpringBootTest
public class FormatterDateTest {
    @DisplayName("FormatterTest return a Date when receive  especific number")
    @Test
    void testFormatterTestReturnADateWhenReceiveANumber(){
        FormatterDate formatter = new FormatterDate();
        LocalDate date = formatter.stringToDate("19870414");
        assertEquals(1987, date.getYear());
        assertEquals(Month.APRIL, date.getMonth());
        assertEquals(14, date.getDayOfMonth());
    }
}
