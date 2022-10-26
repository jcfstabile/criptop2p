package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import java.util.*;

public class FormatterDate {
    public Date stringToDate(String str) {
        int dateNumber = Integer.parseInt(str);
        int year = dateNumber / 10000;
        int month = ((dateNumber - (10000 * year)) / 100);
        int day = (dateNumber - (10000 * year) - (100 * month));
        return new Date(year, month, day);
    }
}
