package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

public class ResponseUnit {
    private String error;

    public ResponseUnit(String message) {
        this.error = message;
    }

    public String getError() {
        return this.error;
    }

}
