package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;

public abstract class TypeIntention {
    TypeName name;

    public TypeIntention(TypeName aTypeName){
        this.name = aTypeName;
    }

    public TypeName getName(){
        return this.name;
    }
    public abstract String shippingAddress(User anUser);
    public abstract boolean isCheck(Intention intention, BigDecimal aCurrentPrice);
    public void verifyIfIsAcepted(User anUser, Intention intention, BigDecimal aCurrentPrice){
        boolean condition = this.isCheck(intention, aCurrentPrice);
        if(condition){
            intention.canceledBySystem();
        }
        else{
            intention.sold();
        }
    }
}
