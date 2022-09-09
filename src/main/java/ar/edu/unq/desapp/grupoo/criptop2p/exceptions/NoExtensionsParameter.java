package ar.edu.unq.desapp.grupoo.criptop2p.exceptions;

public class NoExtensionsParameter extends RuntimeException{
    public NoExtensionsParameter(){
        super("Some parameter does not meet the required extension");
    }
}
