package ar.edu.unq.desapp.grupoo.criptop2p.utils;


import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Formless {
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

    public BigDecimal getTotalInPesos() {
        return this.getTotalInDollars().multiply(new Quoter().quotationOfUsd());
    }

}
