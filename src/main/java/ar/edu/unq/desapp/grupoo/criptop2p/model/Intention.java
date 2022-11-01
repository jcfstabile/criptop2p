package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.utils.TypeIntentionConverter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Convert(converter = TypeIntentionConverter.class)
    private TypeIntention type;
    private CryptoName cryptoName;

    private Status status;

    @JoinColumn(name = "offered_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User offered;

    @Column(name="time_stamp")
    Timestamp timestamp;

    public Intention() {
    }

    public Intention(User anUser, int aCount, BigDecimal aPrice, TypeIntention aType, CryptoName aCryptoName) {
        this.offered = anUser;
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
        this.status = Status.OFFERED;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
    public User getOffered(){ return this.offered; }
    public int getCount(){ return this.count; }
    public BigDecimal getPrice(){ return this.price; }
    public TypeIntention getType(){ return this.type; }
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
        this.type.verifyIfIsAccepted(this, aCurrentPrice);
        this.price = aCurrentPrice;
    }

    public boolean isBiggerThan(BigDecimal aCurrentPrice) {
        return this.compare(aCurrentPrice, 1); //CP > P
    }

    public boolean isSmallerThan(BigDecimal aCurrentPrice) {
        return this.compare(aCurrentPrice, -1); //CP < P
    }

    private boolean compare(BigDecimal aCurrentPrice, int n){
        BigDecimal current = aCurrentPrice.setScale(2, RoundingMode.HALF_UP);
        return current.compareTo(this.price) == n;
    }

    public void sold(Timestamp aTimeStamp, User anDemander){
        Integer reward = this.reward(aTimeStamp);
        this.offered.addPoints(reward);
        anDemander.addPoints(reward);
        this.sold();
        anDemander.addIntention(this);
    }

    public void sold() {
        this.setStatus(Status.SOLD);
    }

    public boolean isItsOfferer(User anUser) {
        return this.offered.isSameUser(anUser);
    }

    //[U1I, U2A,U3]

    public void cancel(User user) {
        if(this.isItsOfferer(user)){
            this.canceled();
        }
        else{
            this.offered();
        }
        user.applyPenalty(20);
    }

    public void offered() {
        this.setStatus(Status.OFFERED);
    }

    public boolean hasStatus(Status aStatus) {
        return this.status.equals(aStatus);
    }

    public int reward(Timestamp anAceptationTimeSt) {
        if(this.differenceBetweenCreationAndAceptation(anAceptationTimeSt) <= TimeUnit.MINUTES.toMillis(30)){
            return 10;
        }
        else{
            return 5;
        }
    }

    private long differenceBetweenCreationAndAceptation(Timestamp anAceptationTimeSt) {
        long now = anAceptationTimeSt.getTime();
        long before = this.timestamp.getTime();
        return now - before;
    }

    public Timestamp getTimeStamp() {
        return this.timestamp;
    }

    public void waitingForTransfer(){
        this.setStatus(Status.WAITINGFORTRANSFER);
    }

    public void waitingForDelivery(){
        this.setStatus(Status.WAITINGFORDELIVERY);
    }

    public boolean isAfter(Date when) {
        return this.timestamp.after(when);
    }

    public boolean isBefore(Date when) {
        return this.timestamp.before(when);
    }

    public boolean isBetween(Date init, Date end) {
        return this.isAfter(init) || this.isBefore(end);
    }
}
