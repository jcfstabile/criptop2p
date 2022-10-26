package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;
import java.util.Date;

public class Report {
    private final CryptoName crypto;
    private final BigDecimal totalInDollars;
    private final BigDecimal currentPrice;
    private final int amount;

//Fsalta Monto de la cotización en pesos ARG

    public Report(CryptoName aCrypto, BigDecimal aTotalInDollars, int anAmount, BigDecimal currentPrice) {
        this.crypto = aCrypto;
        this.totalInDollars = aTotalInDollars;
        this.amount = anAmount;
        this.currentPrice = currentPrice;
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
}
