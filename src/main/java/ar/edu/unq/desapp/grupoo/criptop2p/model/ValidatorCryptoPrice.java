package ar.edu.unq.desapp.grupoo.criptop2p.model;

public class ValidatorCryptoPrice {
    private Long percent;
    public ValidatorCryptoPrice(){
        this.percent = (long) 5;
    }

    public Long getPercent() { return percent; }
    public void setPerCent(Long aPercent){
        this.percent = aPercent;
    }

    public Intention createIntention(User anUser, Integer aCount, Long aPrice, Type aType, CryptoName aCryptoName, Long currentPrice) {
        Intention intention = new Intention(anUser, aCount, aPrice, aType, aCryptoName);
        if(!this.isCorrectPrice(aPrice, currentPrice)){
            intention.setStatus(Status.CANCELEDBYSYSTEM);
        }
        return intention;
    }

    private Boolean isCorrectPrice(Long aPrice, Long currentPrice){
        Long fivePerCent = aPrice * (long) 5 / (long) 100;
        Long more = aPrice + fivePerCent;
        Long less = aPrice - fivePerCent;
        return currentPrice >= less && currentPrice <= more;
    }
}
