package ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions;

import java.util.List;

public class UserConstraintViolationException extends RuntimeException {
    private List<String> errors;
    public UserConstraintViolationException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}
