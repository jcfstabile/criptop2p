package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;

public class Sell extends TypeIntention {

    public Sell(){
        super(TypeName.SELL);
    }
    @Override
    public String shippingAddress(User anUser) {
        return anUser.getCvu();
    }

    @Override
    public boolean isCheck(Intention intention, BigDecimal aCurrentPrice) {
       return intention.isSmallerThan(aCurrentPrice);
    }
}
