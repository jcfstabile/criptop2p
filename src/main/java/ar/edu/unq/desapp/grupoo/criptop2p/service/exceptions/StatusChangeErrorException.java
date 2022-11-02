package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;

public class StatusChangeErrorException extends RuntimeException {

    public final Status status;

    public StatusChangeErrorException(Status status) {
       this.status = status;
    }

}
