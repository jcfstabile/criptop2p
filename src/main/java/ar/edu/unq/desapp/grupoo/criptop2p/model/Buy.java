package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;

public class Buy extends TypeIntention {

    public Buy(){
        super(TypeName.BUY);
    }
    public String shippingAddress(User anUser){
        return anUser.getWalletAddress();
    }
    public boolean isCheck(Intention intention, BigDecimal aCurrentPrice){
        return intention.isBiggerThan(aCurrentPrice);
    }
}
