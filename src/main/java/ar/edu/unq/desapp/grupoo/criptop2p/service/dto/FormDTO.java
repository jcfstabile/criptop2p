package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FormDTO {
    private final List<ReportDTO> reportDTOS;
    private final Date date;

    private final BigDecimal totalInDollars;
    private final BigDecimal totalInPesos;

    public FormDTO(List<ReportDTO> reportDTOS, Quoter quoter) {
        this.reportDTOS = reportDTOS;
        date = new Date();
        this.totalInDollars = this.getTotalInDollars();
        this.totalInPesos = this.getTotalInPesos(quoter);
    }

    public List<ReportDTO> getReports(){
        return this.reportDTOS;
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
        return this.reportDTOS.stream().map(ReportDTO::getTotalInDollars).reduce(new BigDecimal(0), BigDecimal::add);
    }

    public BigDecimal getTotalInPesos(Quoter aQuoter) {
        return this.totalInDollars.multiply(aQuoter.quotationOfUsd());
    }
}
