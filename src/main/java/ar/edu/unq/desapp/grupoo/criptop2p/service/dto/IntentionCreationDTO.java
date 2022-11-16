package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

public class IntentionCreationDTO {
    private final int count;
    private final String price;
    private final String type;
    private final String cryptoName;

    public IntentionCreationDTO(int aCount, String aPrice, String aType, String aCryptoName) {
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
    }

    public int getCount(){
        return this.count;
    }

    public String getPrice(){
        return this.price;
    }

    public String getType(){
        return this.type;
    }

    public String getCryptoName(){
        return this.cryptoName;
    }
}


