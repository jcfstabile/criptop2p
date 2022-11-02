package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class InterruptedErrorException extends RuntimeException {
    public InterruptedErrorException() {
        super("Server operation has been interrupted");
    }
}
