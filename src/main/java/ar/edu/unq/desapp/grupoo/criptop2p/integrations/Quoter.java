package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class Quoter extends Integrator{
    public Quoter() {
        super("https://www.dolarsi.com/api/api.php?type=valoresprincipales");
    }

    public BigDecimal quotationOfUsd(){
        JsonNode root = this.query(this.completeUrl(""));
        JsonNode value = root.path(0);
        String quotation= value.path("casa").path("compra").asText().replace(",", ".");
        return new BigDecimal(quotation);
    }

    @Override
    protected RuntimeException myException(IOException ex) {
        return new RuntimeException(ex);
    }
}
