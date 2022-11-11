package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class IncorrectUserException extends RuntimeException {
    public IncorrectUserException(Long id) {
        super("The user with ID: " + id.toString() + " cant accepted this intention because its is the intention offered");
    }
}
