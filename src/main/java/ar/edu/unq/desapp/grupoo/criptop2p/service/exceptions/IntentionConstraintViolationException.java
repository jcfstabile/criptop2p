package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

import java.util.List;

public class IntentionConstraintViolationException extends RuntimeException {
    private final List<String> errors;
    public IntentionConstraintViolationException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}
