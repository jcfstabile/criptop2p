package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import java.math.BigDecimal;

public class IntentionCreationDTO {
    private final int count;
    private final BigDecimal price;

    private final String type;
    private final CryptoName cryptoName;
    public IntentionCreationDTO(int aCount, String aPrice, String aType, String aCryptoName) {
        this.count = aCount;
        this.price= new BigDecimal(aPrice);
        this.type = aType;
        this.cryptoName = CryptoName.valueOf(aCryptoName);
    }

    public int getCount(){
        return this.count;
    }

    public BigDecimal getPrice(){
        return this.price;
    }

    public String getType(){
        return this.type;
    }

    public CryptoName getCryptoName(){
        return this.cryptoName;
    }
}


