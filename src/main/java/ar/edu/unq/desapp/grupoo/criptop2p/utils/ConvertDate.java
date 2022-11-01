package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ConvertDate {
    public Date convertToDate(LocalDate of) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(of.atStartOfDay(defaultZoneId).toInstant());
    }
}
