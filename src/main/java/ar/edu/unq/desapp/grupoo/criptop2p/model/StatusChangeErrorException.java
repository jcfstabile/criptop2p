package ar.edu.unq.desapp.grupoo.criptop2p.model;

public class StatusChangeErrorException extends RuntimeException {

    public Status status;

    public StatusChangeErrorException(Status status) {
       this.status = status;
    }

}
