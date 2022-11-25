package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class IntentionCreationDTO {
    @Schema(example = "12")
    private final int count;
    @Schema(example = "17032.28700000")
    private final String price;
    @Schema(example = "SELL")
    private final String type;
    @Schema(example = "BTCUSDT")
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


