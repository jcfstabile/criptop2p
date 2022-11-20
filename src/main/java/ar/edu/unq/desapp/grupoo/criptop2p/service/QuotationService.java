package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CachedQuotations;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Quotation;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.QuotationsRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.CryptoNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.InternalErrorProcessingQuotationsException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.interfaces.QuotationServiceInterface;
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

@Service
@Component
public class QuotationService implements QuotationServiceInterface {

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
    @Override
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

    @Override
    public synchronized List<QuotationDTO> allQuotations() throws InterruptedException {
        while(busy || invalidCache) { wait(); }
        setBusy();
        List<QuotationDTO> quotations = cachedQuotations;
        clearBusy();
        notifyAll();
        return quotations;
    }

    @Override
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
    public List<TimedQuotationDTO> last24hsOf(String quotationName){
        var ref = new Object() { CryptoName cryptoName = null; };
        try {
            ref.cryptoName = CryptoName.valueOf(quotationName);
        } catch (IllegalArgumentException e) {
            throw new CryptoNotFoundException(quotationName);
        }
        List<TimedQuotationDTO> timedQuotationDTOs = new ArrayList<>();
        int millisOf24hs = 864000000;
        Timestamp now24hsBefore = new Timestamp(System.currentTimeMillis() - millisOf24hs);
        this.quotationsRepository.findAll()
                                 .forEach(quotations -> {
                                     if (quotations.getTimeStamp().after(now24hsBefore)) {
                                         try {
                                             timedQuotationDTOs.add(
                                                     timedQuotationDTO(quotations.getTimeStamp(),
                                                                       quotations.getQuotation(ref.cryptoName))
                                                     );
                                         } catch (IOException e) {
                                             throw new InternalErrorProcessingQuotationsException();
                                         }
                                     }
                                 });
        return timedQuotationDTOs;
    }
}
