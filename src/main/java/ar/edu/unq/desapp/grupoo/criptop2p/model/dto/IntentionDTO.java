package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Type;

import java.math.BigDecimal;

public class IntentionDTO {
    private int count;
    private BigDecimal price;

    private Type type;
    private CryptoName cryptoName;
    public IntentionDTO(int aCount, BigDecimal aPrice, Type aType, CryptoName aCryptoName) {
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

    public Type getType(){
        return this.type;
    }

    public CryptoName getCryptoName(){
        return this.cryptoName;
    }
}


