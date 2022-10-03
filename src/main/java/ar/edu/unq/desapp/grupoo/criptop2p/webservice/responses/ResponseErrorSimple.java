package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

public class ResponseErrorSimple extends ResponseError{
    private final String error;
    public ResponseErrorSimple(String errorCode, String message, String error) {
        super(errorCode, message);
        this.error = error;
    }

    public Object getError(){
        return this.error;
    }
}
