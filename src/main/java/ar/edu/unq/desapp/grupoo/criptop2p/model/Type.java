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

    public abstract String shippingAddress(User anUser);
    public abstract boolean isCheck(Intention intention, BigDecimal aCurrentPrice);
    public void verifyIfIsAcepted(User anUser, Intention intention, BigDecimal aCurrentPrice){
            boolean condition = this.isCheck(intention, aCurrentPrice);
            if(condition){
                intention.canceledBySystem();
            }
            else{
                intention.sold();
                intention.setDemander(anUser);
                intention.addPoints();
            }
        }
}
