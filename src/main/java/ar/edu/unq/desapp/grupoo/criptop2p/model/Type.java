package ar.edu.unq.desapp.grupoo.criptop2p.model;

public enum Type {
    SELL{
        public String shippingAddress(User anUser){
            return anUser.getCvu();
        }
    }
    ,
    BUY{
        public String shippingAddress(User anUser){
            return anUser.getWalletAddress();
        }
    };

    abstract public String shippingAddress(User anUser);
}
