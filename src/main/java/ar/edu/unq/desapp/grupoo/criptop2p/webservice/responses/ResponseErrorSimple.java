package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import io.swagger.v3.oas.annotations.media.Schema;

public class ResponseErrorSimple extends ResponseError{
    @Schema(example = "A simple error description")
    private final String error;
    public ResponseErrorSimple(String errorCode, String message, String error) {
        super(errorCode, message);
        this.error = error;
    }

    public Object getError(){
        return this.error;
    }
}
