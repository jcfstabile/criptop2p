package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class ServerCantHandleRequestNowException extends RuntimeException {

    private final String error;

    public ServerCantHandleRequestNowException() {
        this.error = "The server can't handle the request now";
    }

    public String getError() {
        return this.error;
    }
}
