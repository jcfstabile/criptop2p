package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

public class IntentionNotFoundException extends RuntimeException {
    public IntentionNotFoundException(Long intentionId) {
        super("Could not find intention " + intentionId);
    }
}
