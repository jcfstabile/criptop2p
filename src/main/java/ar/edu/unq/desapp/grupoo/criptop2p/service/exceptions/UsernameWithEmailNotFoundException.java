package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class UsernameWithEmailNotFoundException extends RuntimeException {
    private final String error;
    public UsernameWithEmailNotFoundException(String email) {
        this.error = "User with email: " + email + " cant be found";
    }

    public String getError() { return this.error; }
}
