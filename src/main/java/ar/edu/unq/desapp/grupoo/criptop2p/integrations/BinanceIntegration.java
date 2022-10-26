package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.Quotation;
import org.springframework.stereotype.Component;

@Component
public class BinanceIntegration extends Integrator {
    public BinanceIntegration() {
        super("https://api1.binance.com/api/v3/");
    }

    public Quotation priceOf(CryptoName aCryptoName) {
        String resource = "ticker/price?symbol=" + aCryptoName.name();
        return new Quotation(aCryptoName.name(), this.query(this.completeUrl(resource), "price"));
    }

    public String check() {
        return this.query(this.completeUrl("ping"), "result");
    }
}
