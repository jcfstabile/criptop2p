package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Crypto;

public class IntentionDTO {
    public int count;
    public Long price;
    public Crypto crypto;
    public IntentionDTO(int aCount, Long aPrice, Crypto aCrypto) {
        this.count = aCount;
        this.price= aPrice;
        this.crypto = aCrypto;
    }
}


