package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.StatusChangeErrorException;

import java.math.BigDecimal;

public abstract class TypeIntention implements java.io.Serializable{
    TypeName name;

    protected TypeIntention(TypeName aTypeName){
        this.name = aTypeName;
    }

    public TypeName getName(){
        return this.name;
    }
    public abstract String shippingAddress(User anUser);
    public abstract boolean isCheck(Intention intention, BigDecimal aCurrentPrice);
    public void verifyIfIsAccepted(Intention intention, BigDecimal aCurrentPrice) throws StatusChangeErrorException {
        boolean condition = this.isCheck(intention, aCurrentPrice);
        if(condition){
            intention.canceledBySystem();
        } else if (intention.getStatus() == Status.OFFERED) {
            intention.sold();
        }
    }
}
