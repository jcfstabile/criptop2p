package ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions;

import java.io.IOException;

public class BinanceQueryErrorException extends RuntimeException {
    private final String error;

    public BinanceQueryErrorException(IOException rtex) {
        this.error = rtex.getMessage();
    }

    public String getError() {
        return error;
    }
}