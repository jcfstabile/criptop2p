package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class UsernameWithEmailNotFoundException extends RuntimeException {

    public UsernameWithEmailNotFoundException(String email) {
        super("User with email: " + email + " cant be found");
    }
}
