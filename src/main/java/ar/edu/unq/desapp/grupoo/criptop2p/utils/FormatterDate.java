package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IncorrectDateFormatException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FormatterDate {
    public LocalDate stringToDate(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
        LocalDate date;
        try{
            date = LocalDate.parse(str, formatter);
        }
        catch(DateTimeParseException ex){
            throw new IncorrectDateFormatException(ex);
        }
        return date;
    }
}
