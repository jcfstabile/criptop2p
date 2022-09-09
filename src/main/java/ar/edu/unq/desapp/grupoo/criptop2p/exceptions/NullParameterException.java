package ar.edu.unq.desapp.grupoo.criptop2p.exceptions;

public class NullParameterException extends RuntimeException{
    public NullParameterException(){
        super("Some parameter/s is/are null");
    }
}
