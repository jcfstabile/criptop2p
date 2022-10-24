package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValidatorCryptoPrice {
    private BigDecimal percent;
    public ValidatorCryptoPrice(){
        this.percent = new BigDecimal(5);
    }

    public BigDecimal getPercent() { return percent; }
    public void setPerCent(BigDecimal aPercent){
        this.percent = aPercent;
    }

    public Intention createIntention(User anUser, Integer aCount, BigDecimal aPrice, TypeIntention aType, CryptoName aCryptoName, BigDecimal currentPrice) {
        Intention intention = new Intention(anUser, aCount, aPrice, aType, aCryptoName);
        if(!this.isCorrectPrice(aPrice, currentPrice)){
            intention.canceledBySystem();
        }
        return intention;
    }

    private boolean isCorrectPrice(BigDecimal aPrice, BigDecimal currentPrice){
        BigDecimal fivePerCent = aPrice.setScale(2, RoundingMode.HALF_UP).multiply(this.percent).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal more = aPrice.setScale(2, RoundingMode.HALF_UP).add(fivePerCent).setScale(2, RoundingMode.HALF_UP);
        BigDecimal less = aPrice.setScale(2, RoundingMode.HALF_UP).subtract(fivePerCent).setScale(2, RoundingMode.HALF_UP);
        return currentPrice.setScale(2, RoundingMode.HALF_UP).compareTo(less) >= 0
                && currentPrice.setScale(2, RoundingMode.HALF_UP).compareTo(more) <= 0 ;
    }
}
