package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Report;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InspectUser {
    public List<Report> offersBetween(User anUser, Date init, Date end) {
        return this.reportsOf(anUser.offersBetween(init, end));
    }

    private List<Report> reportsOf(List<Intention> intentions){
        return this.intentionsClasifficatedBySymbol(intentions).stream().map(listOfIntention ->
                new Report(this.crypto(listOfIntention),
                           this.costInDollars(listOfIntention),
                           this.amount(listOfIntention))).toList();
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