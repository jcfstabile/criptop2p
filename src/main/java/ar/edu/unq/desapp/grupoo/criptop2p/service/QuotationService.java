package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.Quotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    @Autowired
    BinanceIntegration binanceIntegrator;

    public List<Quotation> allQuotations() {
        List cryptos = Arrays.asList(CryptoName.values());
        return (List<Quotation>) cryptos.stream().map(crypto ->
            new Quotation(crypto.toString(), binanceIntegrator.priceOf((CryptoName)crypto))).collect(Collectors.toList());
    }
}
