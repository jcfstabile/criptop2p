package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CachedQuotations;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Quotation;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.QuotationsRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Long.parseLong;

@Service
@Component
public class QuotationService {

    @Autowired
    BinanceIntegration binanceIntegrator;

    @Autowired
    QuotationsRepository quotationsRepository;

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

    @Transactional
    public synchronized void saveCachedQuotations() throws JsonProcessingException, InterruptedException {
        while(busy || invalidCache) { wait(); }
        setBusy();
        List<QuotationDTO> quotations = cachedQuotations;
        clearBusy();
        notifyAll();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        List<Quotation> cs =
                quotations.stream()
                        .map(quotationDTO ->
                                new Quotation(CryptoName.valueOf(quotationDTO.getCryptoName()),
                                              new BigDecimal(quotationDTO.getPrice())
                                )
                        )
                        .toList();
        CachedQuotations cq = new CachedQuotations(ts, cs);
        quotationsRepository.save(cq);
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

    private TimedQuotationDTO timedQuotationDTO(Timestamp ts, Quotation quotation){
        return new TimedQuotationDTO(ts, quotation.getCryptoName(), quotation.getPrice());
    }
    public List<TimedQuotationDTO> last24hsOf(String quotationName) {
        CryptoName cryptoName = CryptoName.valueOf(quotationName);
        List<TimedQuotationDTO> timedQuotationDTOs = new ArrayList<>();
        Timestamp now24hsBefore = new Timestamp(System.currentTimeMillis() - 86400000);
        this.quotationsRepository.findAll()
                                 .forEach(cachedQuotations -> {
                                     if (cachedQuotations.getTimeStamp().after(now24hsBefore)) {
                                         try {
                                             timedQuotationDTOs.add(
                                                     timedQuotationDTO(cachedQuotations.getTimeStamp(),
                                                                       cachedQuotations.getQuotation(cryptoName))
                                                     );
                                         } catch (IOException e) {}
                                     }
                                 });
        return timedQuotationDTOs;
    }
}
