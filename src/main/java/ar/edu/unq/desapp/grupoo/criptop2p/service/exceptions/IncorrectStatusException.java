package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class IncorrectStatusException extends RuntimeException {
    public IncorrectStatusException(String aWrongStatus) {
        super("The next status is a incorrect status for an intention: " + aWrongStatus);
    }
}
