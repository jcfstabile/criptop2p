package ar.edu.unq.desapp.grupoo.criptop2p.exceptions;

public class IncorrectFormatMail extends RuntimeException {
    public IncorrectFormatMail(){
        super("The email format is incorrect");
    }
}
