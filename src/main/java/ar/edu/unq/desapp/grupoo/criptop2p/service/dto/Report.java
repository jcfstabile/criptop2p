package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


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

    public BigDecimal getCurrentPriceInPesos(){ return this.currentPriceInPesos; }

    public static class Formless {
        private final List<Report> reports;
        private final Date date;

        public Formless(List<Report> reports) {
            this.reports = reports;
            date = new Date();
        }

        public List<Report> getReports(){
            return this.reports;
        }

        public Date getDate(){
            return this.date;
        }

        public BigDecimal getTotalInDollars() {
            return this.reports.stream().map(Report::getTotalInDollars).reduce(new BigDecimal(0), BigDecimal::add);
        }

        public BigDecimal getTotalInPesos(Quoter aQuoter) {
            return this.getTotalInDollars().multiply(aQuoter.quotationOfUsd());
        }
    }
}
