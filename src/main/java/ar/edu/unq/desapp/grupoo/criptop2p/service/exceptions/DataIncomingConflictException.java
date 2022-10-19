package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class DataIncomingConflictException extends RuntimeException {
    private final String error;

    public DataIncomingConflictException() {
        this.error = "The operation can not be completed due to data conflict";
    }

    public String getError(){
        return this.error;
    }
}
