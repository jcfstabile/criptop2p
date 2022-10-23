package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import java.math.BigDecimal;

public class IntentionCreationDTO {
    private final int count;
    private final BigDecimal price;

    private final String type;
    private final CryptoName cryptoName;
    public IntentionCreationDTO(int aCount, BigDecimal aPrice, String aType, CryptoName aCryptoName) {
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
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


