package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;

public class Quotation {

    private CryptoName cryptoName;
    private BigDecimal price;

    public Quotation(){}

    public Quotation(CryptoName name, BigDecimal value) {
        this.cryptoName = name;
        this.price = value;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CryptoName getCryptoName() {
        return cryptoName;
    }
}
