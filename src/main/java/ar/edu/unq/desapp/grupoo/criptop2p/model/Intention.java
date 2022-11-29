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
public class Intention implements java.io.Serializable{
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
    @Column(precision = 12, scale=8)
    private BigDecimal price;

    @Convert(converter = TypeIntentionConverter.class)
    private TypeIntention type;
    private CryptoName cryptoName;

    private Status status;

    private boolean halfDone;

    @JoinColumn(name = "offered_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User offered;

    @Column(name="time_stamp")
    Timestamp timestamp;

    public Intention() {
        this.status = Status.OFFERED;
    }

    public Intention(User anUser, int aCount, BigDecimal aPrice, TypeIntention aType, CryptoName aCryptoName) {
        this.offered = anUser;
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
        this.status = Status.OFFERED;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.halfDone = false;
    }
    public User getOffered(){ return this.offered; }
    public int getCount(){ return this.count; }
    public BigDecimal getPrice(){ return this.price; }
    public TypeIntention getType(){ return this.type; }
    public CryptoName getCrypto(){ return this.cryptoName; }
    public Status getStatus() { return this.status; }
    public void setStatus(Status aStatus){
        halfDone = isHalfOfTransaction(aStatus);
        status = status.changeTo(aStatus);
    }

    private boolean isHalfOfTransaction(Status aStatus){
        return aStatus == Status.WAITINGFORDELIVERY || aStatus == Status.WAITINGFORTRANSFER;
    }

    public void canceledBySystem(){
        this.setStatus(Status.CANCELEDBYSYSTEM);
    }

    public void canceled(){
        this.setStatus(Status.CANCELED);
    }

    public void verifyIfIsAcepted(BigDecimal aCurrentPrice){
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
        int reward = this.reward(aTimeStamp);
        this.offered.addPoints(reward);
        anDemander.addPoints(reward);
        this.sold();
        anDemander.addIntention(this);
    }

    public void sold(){
        this.setStatus(Status.SOLD);
    }

    public boolean isItsOfferer(User anUser) {
        return this.offered.isSameUser(anUser);
    }

    //[U1I, U2A,U3]

    public void cancel(User user){
        if(this.isItsOfferer(user)){
            this.canceled();
        }
        else{
            this.offered();
        }
        user.applyPenalty(20);
    }

    public void offered(){
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

    public void deliveryDone(){
        this.setStatus(halfDone ? Status.CLOSED : Status.WAITINGFORTRANSFER);
    }

    public void transferDone(){
        this.setStatus(halfDone ? Status.CLOSED : Status.WAITINGFORDELIVERY);
    }

    private boolean isAfter(Date when) {
        return this.timestamp.after(when);
    }

    private boolean isBefore(Date when) {
        return this.timestamp.before(when);
    }

    public boolean isBetween(Date init, Date end) {
        return this.isAfter(init) && this.isBefore(end);
    }

    public boolean isItsDemander(User anUser) {
        return anUser.getOffers().contains(this) && ! anUser.isSameUser(this.offered);
    }
}
