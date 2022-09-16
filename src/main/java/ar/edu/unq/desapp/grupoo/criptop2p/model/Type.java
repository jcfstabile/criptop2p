package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;

public enum Type {
    SELL{
        public String shippingAddress(User anUser){
            return anUser.getCvu();
        }
        public boolean isCheck(Intention intention, BigDecimal aCurrentPrice){
            return intention.isSmallerThan(aCurrentPrice);
        }
    }
    ,
    BUY{
        public String shippingAddress(User anUser){
            return anUser.getWalletAddress();
        }
        public boolean isCheck(Intention intention, BigDecimal aCurrentPrice){
            return intention.isBiggerThan(aCurrentPrice);
        }
    };

    abstract public String shippingAddress(User anUser);
    abstract public boolean isCheck(Intention intention, BigDecimal aCurrentPrice);
    public void verifyIfIsAcepted(Intention intention, BigDecimal aCurrentPrice){
            if(this.isCheck(intention, aCurrentPrice)){
                intention.canceledBySystem();
            }
            intention.sold();
        }
}
