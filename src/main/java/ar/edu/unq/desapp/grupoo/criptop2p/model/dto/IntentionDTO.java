package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Type;

import java.math.BigDecimal;

public class IntentionDTO {
    public int count;
    public BigDecimal price;

    public Type type;
    public CryptoName cryptoName;
    public IntentionDTO(int aCount, BigDecimal aPrice, Type aType, CryptoName aCryptoName) {
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
    }
}


