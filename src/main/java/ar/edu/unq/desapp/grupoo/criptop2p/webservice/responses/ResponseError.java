package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import io.swagger.v3.oas.annotations.media.Schema;


abstract class ResponseError {

    @Schema(example = "001")
    private final String errorCode;
    @Schema(example = "Error message")
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
