package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;


public class StatusChangeNotAllowedRestException extends RuntimeException {

    private final String error;

    public StatusChangeNotAllowedRestException (Status nextStatus) {
       this.error = "Cant change the intention to " + nextStatus.toString();
    }

    public String getError() { return this.error; }
}
