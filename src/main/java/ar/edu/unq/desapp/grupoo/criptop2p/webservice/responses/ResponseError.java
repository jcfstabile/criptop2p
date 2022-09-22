package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import java.util.List;

public class ResponseError {

    private String errorCode;
    private String message;

    private Object error;

    public ResponseError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    public ResponseError(String errorCode, String message, String error) {
        this(errorCode, message);
        this.error = error;
    }

    public ResponseError(String errorCode, String message, List<String> errors){
        this(errorCode, message);

        this.error = errors;
    }

    public String getErrorCode() { return this.errorCode; }
    public String getMessage() {
        return this.message;
    }

    public Object getError(){
        return this.error;
    }


}
