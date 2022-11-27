package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Quoter;
import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.FormDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.ReportDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InspectUser {

    public FormDTO offersBetween(User anUser, Date init, Date end, Quoter aQuoter) {
        return new FormDTO(this.reportsOf(anUser.offersBetween(init, end)), aQuoter);
    }

    private List<ReportDTO> reportsOf(List<Intention> intentions){
        return this.intentionsClasifficatedBySymbol(intentions).stream().map(listOfIntention -> {
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(new BinanceIntegration().priceOf(listOfIntention.get(0).getCrypto()).getPrice()));
                return new ReportDTO(this.crypto(listOfIntention),
                           this.costInDollars(listOfIntention),
                           this.amount(listOfIntention),
                           price,
                           new Quoter().quotationOfUsd().multiply(price));
                }).toList();
    }

    private CryptoName crypto(List<Intention> intentions){
        return intentions.get(0).getCrypto();
    }

    private BigDecimal costInDollars(List<Intention> intentions){
        return intentions.stream().map(Intention::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int amount(List<Intention> intentions){
        return intentions.stream().map(Intention::getCount).reduce(0, Integer::sum);
    }

    private List<List<Intention>> intentionsClasifficatedBySymbol(List<Intention> intentions) {
        List<CryptoName> cryptos = this.cryptosOf(intentions);
        return cryptos.stream().map( cryptoName -> this.intentionsOf(cryptoName, intentions)).toList();
    }

    private List<CryptoName> cryptosOf(List<Intention> intentions){
        return intentions.stream().map(Intention::getCrypto).collect(Collectors.toSet()).stream().toList();
    }

    private List<Intention> intentionsOf(CryptoName aCrypto, List<Intention> intentions){
        return intentions.stream().filter(intention-> intention.getCrypto().equals(aCrypto)).toList();
    }
}
