package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;

import java.math.BigDecimal;

public class IntentionDTO {
    private final int count;
    private final BigDecimal price;

    private final TypeIntention type;
    private final CryptoName cryptoName;
    public IntentionDTO(int aCount, BigDecimal aPrice, TypeIntention aType, CryptoName aCryptoName) {
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

    public TypeIntention getType(){
        return this.type;
    }

    public CryptoName getCryptoName(){
        return this.cryptoName;
    }
}


