package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Crypto;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Type;

public class IntentionDTO {
    public int count;
    public Long price;

    public Type type;
    public Crypto crypto;
    public IntentionDTO(int aCount, Long aPrice, Type aType, Crypto aCrypto) {
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.crypto = aCrypto;
    }
}


