package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class CryptoNotFoundException extends RuntimeException {
    public CryptoNotFoundException(String cryptoName) {
        super("The crypto with name " + cryptoName + " was not found");
    }
}
