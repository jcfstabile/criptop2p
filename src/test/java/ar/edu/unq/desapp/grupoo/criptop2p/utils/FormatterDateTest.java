package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IncorrectDateFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("FormatterDate Tests")
@SpringBootTest
class FormatterDateTest {
    @DisplayName("FormatterTest return a Date when receive a especific String")
    @Test
    void testFormatterTestReturnADateWhenReceiveANumber(){
        FormatterDate formatter = new FormatterDate();
        LocalDate date = formatter.stringToDate("04/14/1987");
        assertEquals(1987, date.getYear());
        assertEquals(4, date.getMonth().getValue());
        assertEquals(14, date.getDayOfMonth());
    }

    @DisplayName("FormatterTest throw a exception when string can't be parse to Date")
    @Test
    void testFormatterThrowsAnException (){
        FormatterDate formatter = new FormatterDate();
        IncorrectDateFormatException exception = assertThrows(IncorrectDateFormatException.class, () -> {
            formatter.stringToDate("42/42/1987");
        });
        assertEquals("Text '42/42/1987' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 42", exception.getMessage());
    }
}
