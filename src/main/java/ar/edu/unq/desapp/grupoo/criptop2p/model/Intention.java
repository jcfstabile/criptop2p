package ar.edu.unq.desapp.grupoo.criptop2p.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name="intentions")
public class Intention {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private int count;
    private BigDecimal price;

    private Type type;
    private CryptoName cryptoName;

    private Status status;

    @JoinColumn(name = "offered_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User offered;
    @JoinColumn(name = "demander_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User demander;


    public Intention() {
    }

    public Intention(User anUser, int aCount, BigDecimal aPrice, Type aType, CryptoName aCryptoName) {
        this.offered = anUser;
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
        this.status = Status.OFFERED;
        this.demander = null;
    }
    public User getOffered(){ return this.offered; }
    public User getDemander(){ return this.demander; }
    public int getCount(){ return this.count; }
    public BigDecimal getPrice(){ return this.price; }
    public Type getType(){ return this.type; }
    public CryptoName getCrypto(){ return this.cryptoName; }
    public Status getStatus() { return this.status; }
    public void setStatus(Status aStatus){ this.status = aStatus; }

    public void canceledBySystem() {
        this.setStatus(Status.CANCELEDBYSYSTEM);
    }

    public void canceled() {
        this.setStatus(Status.CANCELED);
    }

    public void verifyIfIsAcepted(BigDecimal aCurrentPrice) {
        this.type.verifyIfIsAcepted(this, aCurrentPrice);
        this.price = aCurrentPrice;
    }

    public boolean isBiggerThan(BigDecimal aCurrentPrice) {
        return this.compare(aCurrentPrice, 1); //CP > P
    }

    public boolean isSmallerThan(BigDecimal aCurrentPrice) {
        return this.compare(aCurrentPrice, -1); //CP < P
    }

    private boolean compare(BigDecimal aCurrentPrice, int n){
        //aCurrentPrice >/< n
        return aCurrentPrice.setScale(2, RoundingMode.HALF_UP).compareTo(this.price.setScale(2, RoundingMode.HALF_UP)) == n;
    }

    public void setDemander(User user) {
        this.demander = user;
    }

    public void sold() {
        this.setStatus(Status.SOLD);
    }

    public boolean isItsOfferer(User anUser) {
        return this.isSameUser(this.offered, anUser);
    }

    public boolean isItsDemander(User anUser){
        return this.demander != null && this.isSameUser(this.demander, anUser);
    }

    private boolean isSameUser(User anUser, User otherUser){
        return  anUser.getId() == otherUser.getId()
                && anUser.getWalletAddress() == otherUser.getWalletAddress()
                && anUser.getEmail() == otherUser.getEmail()
                && anUser.getCvu() == otherUser.getCvu();
    }

    public void cancel(User user) {
        if(this.isItsOfferer(user)){
            this.canceled();
        }
        if(this.isItsDemander(user)){
            this.offered();
            this.demander = null;
        }
    }

    public void offered() {
        this.setStatus(Status.OFFERED);
    }
}
