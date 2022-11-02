package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Convert Date test")
public class ConvertDateTest {
    ConvertDate convertDate;
    @BeforeEach
    void setUp(){
        convertDate = new ConvertDate();
    }

    @DisplayName("Convert parse from a LocalDate to a date")
    @Test
    void testConvertParseFromALocalDateToADate() throws ParseException {
        Date date = convertDate.convertToDate(LocalDate.of(1987, 12, 25));
        String dateString = "25-12-1987";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateExpected = formatter.parse(dateString);
        assertEquals(dateExpected, date);
    }
}
