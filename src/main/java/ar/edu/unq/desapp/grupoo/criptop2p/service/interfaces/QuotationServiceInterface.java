package ar.edu.unq.desapp.grupoo.criptop2p.service.interfaces;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.List;

public interface QuotationServiceInterface {

    List<TimedQuotationDTO> last24hsOf(String quotationName);
    BigDecimal priceOf(CryptoName cryptoName) throws InterruptedException;
    void saveCachedQuotations() throws JsonProcessingException, InterruptedException;
    List<QuotationDTO> allQuotations() throws InterruptedException;

}
