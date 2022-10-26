package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;
import java.math.BigDecimal;


public class Report {
    private final CryptoName crypto;
    private final BigDecimal totalInDollars;
    private final BigDecimal currentPrice;
    private final BigDecimal currentPriceInPesos;

    private final int amount;

    public Report(CryptoName aCrypto, BigDecimal aTotalInDollars, int anAmount, BigDecimal currentPrice, BigDecimal currentPriceInPesos) {
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

    public BigDecimal getPriceInPesos(Quoter aQuoter){ return this.currentPrice; }
}
