package ar.edu.unq.desapp.grupoo.criptop2p.model;

public enum Status implements State{
    CANCELED           {},
    SOLD               { @Override public boolean allowed(Status next){
        return next != CLOSED;}
    },
    OFFERED            { @Override public boolean allowed(Status next){
        return next == SOLD || next == CANCELED || next == CANCELEDBYSYSTEM; }
    },
    CANCELEDBYSYSTEM   {},

    WAITINGFORTRANSFER { @Override public boolean allowed(Status next){
        return next == CLOSED || next == WAITINGFORDELIVERY || next == CANCELED ;}
    },
    WAITINGFORDELIVERY { @Override public boolean allowed(Status next){
        return next == CLOSED || next == WAITINGFORTRANSFER || next == CANCELED ;}
    },
    CLOSED{}
}

interface State{
    default boolean allowed(Status s) { return true; }

    default Status changeTo(Status status) throws StatusChangeErrorException {
        if (! this.allowed(status)) { throw new StatusChangeErrorException(status); }
        return status;
    }
}