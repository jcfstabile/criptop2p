package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.Quotation;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class BinanceIntegration extends Integrator {
    public BinanceIntegration() {
        super("https://api1.binance.com/api/v3/");
    }

    public Quotation priceOf(CryptoName aCryptoName) {
        String resource = "ticker/price?symbol=" + aCryptoName.name();
        JsonNode root = this.query(this.completeUrl(resource));
        JsonNode value = root.path("price");
        return new Quotation(aCryptoName.name(), value.asText());
    }

    public String check() {
        return this.query(this.completeUrl("ping")).path("result").asText();
    }
}
