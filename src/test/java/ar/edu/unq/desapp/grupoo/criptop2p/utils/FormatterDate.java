package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import java.time.LocalDate;

public class FormatterDate {
    public LocalDate stringToDate(String str) {
        int dateNumber = Integer.parseInt(str);
        int year = dateNumber / 10000;
        int month = ((dateNumber - (10000 * year)) / 100);
        int day = (dateNumber - (10000 * year) - (100 * month));
        return LocalDate.of(year, month, day);
    }
}
