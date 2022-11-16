package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Form {
    private final List<Report> reports;
    private final Date date;

    private final BigDecimal totalInDollars;
    private final BigDecimal totalInPesos;

    public Form(List<Report> reports, Quoter quoter) {
        this.reports = reports;
        date = new Date();
        this.totalInDollars = this.getTotalInDollars();
        this.totalInPesos = this.getTotalInPesos(quoter);
    }

    public List<Report> getReports(){
        return this.reports;
    }

    public Date getDate(){
        return this.date;
    }

    public BigDecimal getTotalPesos() {
        return this.totalInPesos;
    }

    public BigDecimal getTotalDollars() {
        return this.totalInDollars;
    }

    public BigDecimal getTotalInDollars() {
        return this.reports.stream().map(Report::getTotalInDollars).reduce(new BigDecimal(0), BigDecimal::add);
    }

    public BigDecimal getTotalInPesos(Quoter aQuoter) {
        return this.totalInDollars.multiply(aQuoter.quotationOfUsd());
    }
}
