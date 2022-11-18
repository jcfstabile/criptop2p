package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimedQuotationDTO {
    @Schema(example = "xxxxxx xxxxxxx 102")
    private final LocalDateTime dateTime;
    @Schema(example = "CAKEUSDT")
    private final CryptoName cryptoName;
    @Schema(example = "0.12748800")
    private final BigDecimal price;
    public TimedQuotationDTO(Timestamp ts, CryptoName cryptoName, BigDecimal price) {
        dateTime = ts.toLocalDateTime();
        this.cryptoName = cryptoName;
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CryptoName getCryptoName() {
        return cryptoName;
    }

    public String getPrice() {
        return price.toString();
    }
}
