package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
