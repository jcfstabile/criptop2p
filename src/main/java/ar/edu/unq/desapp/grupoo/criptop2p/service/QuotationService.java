package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class QuotationService {

    @Autowired
    BinanceIntegration binanceIntegrator;

    public List<QuotationDTO> allQuotations() {
        List<CryptoName> cryptos = Arrays.asList(CryptoName.values());
        return cryptos.stream().map(crypto ->
            binanceIntegrator.priceOf(crypto)
        ).toList();
    }
}
