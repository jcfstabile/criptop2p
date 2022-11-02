package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

import java.time.format.DateTimeParseException;

public class IncorrectDateFormatException extends RuntimeException {
    public IncorrectDateFormatException(DateTimeParseException ex) {
        super(ex.getMessage());
    }
}
