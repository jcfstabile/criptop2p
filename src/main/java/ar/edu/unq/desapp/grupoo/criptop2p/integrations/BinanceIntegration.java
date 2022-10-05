package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.Integrator;
import org.springframework.stereotype.Component;

@Component
public class BinanceIntegration extends Integrator {
    public BinanceIntegration() {
        super("https://testnet.binance.vision/api/v3/");
    }

    public String priceOf(CryptoName aCryptoName) {
        String resource = "avgPrice?symbol=" + aCryptoName.name();
        return this.query(String.class, this.completeUrl(resource), "price");
    }

    public String check() {
        return this.query(String.class, this.completeUrl("ping"), "result");
    }

    private String completeUrl(String parameters){
        return this.resourceUrl + parameters;
    }
}
