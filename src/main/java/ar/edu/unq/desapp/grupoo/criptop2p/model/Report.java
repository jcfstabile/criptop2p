package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.math.BigDecimal;
import java.util.Date;

public class Report {
    public Date date;
    public CryptoName crypto;
    public BigDecimal costInDollars;
    public int amount;
    public Report(CryptoName aCrypto, BigDecimal aCostInDollars, int anAmount) {
        date = new Date();
        crypto = aCrypto;
        costInDollars = aCostInDollars;
        amount = anAmount;
    }


}
