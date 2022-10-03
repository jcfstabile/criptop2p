package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import java.util.List;

abstract class ResponseError {

    private final String errorCode;
    private final String message;

    public ResponseError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() { return this.errorCode; }
    public String getMessage() {
        return this.message;
    }

}
