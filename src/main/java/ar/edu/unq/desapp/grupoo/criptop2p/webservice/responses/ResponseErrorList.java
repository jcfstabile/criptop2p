package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import java.util.List;

public class ResponseErrorList extends ResponseError{
    private final List<String> errors;
    public ResponseErrorList(String errorCode, String message, List<String> errors) {
        super(errorCode, message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}
