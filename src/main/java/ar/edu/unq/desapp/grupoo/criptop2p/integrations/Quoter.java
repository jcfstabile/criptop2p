package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Integrator;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.BinanceQueryErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

public class Quoter extends Integrator{
    public Quoter() {
        super("https://www.dolarsi.com/api/api.php?type=valoresprincipales");
    }

    public BigDecimal quotationOfUsd(){
        JsonNode root = this.query(this.completeUrl(""));
        JsonNode value = root.path(0);
        String quotation= value.path("casa").path("compra").asText().replaceAll(",", ".");
        return new BigDecimal(quotation);
    }

}
