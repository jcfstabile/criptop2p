package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public class IntentionDTO {
    @Schema(example = "1")
    private final Long intentionId;

    @Schema(example = "1")
    private final int count;

    @Schema(example = "102")
    private final BigDecimal price;

    @Schema(example = "SELL")
    private final TypeName type;

    @Schema(example = "ALICEUSDT")
    private final CryptoName cryptoName;

    @Schema(example = "1")
    private final Long offeredId;

    @Schema(example = "OFFERED")
    private final Status status;

    public IntentionDTO(Long intentionId, int aCount, BigDecimal aPrice, TypeIntention aType, CryptoName aCryptoName, Long offeredId, Status status) {
        this.intentionId = intentionId;
        this.count = aCount;
        this.price= aPrice;
        this.type = aType.getName();
        this.offeredId = offeredId;
        this.cryptoName = aCryptoName;
        this.status = status;
    }

    public Long getIntentionId(){ return this.intentionId; }

    public int getCount(){
        return this.count;
    }

    public BigDecimal getPrice(){
        return this.price;
    }

    public TypeName getType(){
        return this.type;
    }

    public CryptoName getCryptoName(){
        return this.cryptoName;
    }

    public Long getOfferedId() { return this.offeredId; }

    public Status getStatus() { return this.status; }
}


