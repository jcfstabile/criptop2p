package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import java.math.BigDecimal;

public class ReportDTO {
    private final CryptoName crypto;
    private final BigDecimal totalInDollars;
    private final BigDecimal currentPrice;
    private final BigDecimal currentPriceInPesos;

    private final int amount;

    public ReportDTO(CryptoName aCrypto, BigDecimal aTotalInDollars, int anAmount, BigDecimal currentPrice, BigDecimal currentPriceInPesos) {
        this.crypto = aCrypto;
        this.totalInDollars = aTotalInDollars;
        this.amount = anAmount;
        this.currentPrice = currentPrice;
        this.currentPriceInPesos = currentPriceInPesos;
    }

    public int getAmount() {
        return this.amount;
    }

    public CryptoName getCrypto() {
        return this.crypto;
    }

    public BigDecimal getTotalInDollars() {
        return this.totalInDollars;
    }

    public BigDecimal getCurrentPrice() { return this.currentPrice; }

    public BigDecimal getCurrentPriceInPesos(){ return this.currentPriceInPesos; }
    
}
