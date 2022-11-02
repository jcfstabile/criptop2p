package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class NoValidActionErrorException extends RuntimeException {
    public NoValidActionErrorException(String action) {
        super("Action not valid: " + action );
    }
}
