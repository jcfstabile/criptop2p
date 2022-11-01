package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class QuotationService {

    @Autowired
    BinanceIntegration binanceIntegrator;

    protected boolean busy = false;
    protected boolean invalidCache = true;
    protected List<QuotationDTO> cachedQuotations;

    private void setBusy(){ busy = true; }

    private void clearBusy(){ busy = false; }

    private void clearInvalidCache() { invalidCache = false; }


    public synchronized void setCachedQuotations() throws InterruptedException {
        while(busy) { wait(); }
        setBusy();
        cachedQuotations = grabAllQuotations();
        clearBusy();
        clearInvalidCache();
        notifyAll();
    }

    public synchronized List<QuotationDTO> allQuotations() throws InterruptedException {
        while(busy || invalidCache) { wait(); }
        setBusy();
        List<QuotationDTO> quotations = cachedQuotations;
        clearBusy();
        notifyAll();
        return quotations;
    }

    public BigDecimal priceOf(CryptoName cryptoName) throws InterruptedException {
        return new BigDecimal(
                this.allQuotations()
                        .stream()
                        .filter(crypto -> cryptoName.name().equals(crypto.getCryptoName()))
                        .findFirst()
                        .orElse(new QuotationDTO("", "0.0"))
                        .getPrice()
        );
    }


    private List<QuotationDTO> grabAllQuotations() {
        List<CryptoName> cryptos = Arrays.asList(CryptoName.values());
        return cryptos.stream().map(crypto ->
            binanceIntegrator.priceOf(crypto)
        ).toList();
    }
}
