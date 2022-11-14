package ar.edu.unq.desapp.grupoo.criptop2p.model;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IncorrectUserException;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.ValidatorCryptoPrice;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name="users")
@Validated
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name", nullable = false)
    @Size(min= 3, max=30, message ="Name must have between 3 and 30 characters")
    @NotNull(message = "Name cannot be empty or null")
    String name;

    @Column(name="surname", nullable = false)
    @Size(min= 3, max=30, message ="Surname must have between 3 and 30 characters")
    @NotNull(message = "Surname cannot be empty")
    String surname;

    @Column(name="email", nullable = false, unique = true)
    @NotNull(message = "Email cannot be empty")
//    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*)?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:)\\])")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;

    @Column(name="address", nullable = false)
    @Size(min= 10, max=30, message ="Address must have between 10 and 30 characters")
    @NotNull(message = "Address cannot be empty")
    String address;

    @Column(name="password", nullable = false)
    @NotNull(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$._+%@()*`';/=#!?&,]).{6,}$", message = """
            Password must contain:

            - At least one uppercase
            - At least one lowercase
            - At least one digit
            - At least one special character
            - Min 6 characters

            """)
    String password;

    @Column(name="w", nullable = false, unique = true)
    @Size(min= 8, max =8, message ="Wallet Address must have 8 characters")
    @NotNull(message = "Wallet Address cannot be empty")
    String walletAddress;

    @Column(name="cvu", nullable = false)
    @Size(min= 22, max = 22, message ="CVU must have 22 characters")
    @NotNull(message = "CVU cannot be null")
    String cvu;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "offered")
    List<Intention> offers;

    @Column(name="points", nullable = false)
    private int points;

    public User(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;
        this.password = aPassword;
        this.walletAddress = aWalletAddress;
        this.cvu = aCvu;
        this.offers = new ArrayList<>();
        this.points = 0;
    }

    public User() {

    }

    public Long getId(){
        return this.id;
    }
    public String getName() { return this.name; }
    public String getSurname() { return this.surname; }
    public String getEmail() { return this.email; }
    public String getAddress() { return this.address; }
    public String getPassword() { return this.password; }
    public String getWalletAddress() { return this.walletAddress; }
    public String getCvu() { return this.cvu; }

    public void setName(String aName) {
        this.name = aName;
    }
    public void setSurname(String aSurname) {
        this.surname = aSurname;
    }
    public void setEmail(String anEmail) {
        this.email = anEmail;
    }
    public void setAddress(String anAddress) {
        this.address = anAddress;
    }
    public void setPassword(String aPassword) {
        this.password = aPassword;
    }
    public void setWalletAddress(String aWalletAddress) {
        this.walletAddress = aWalletAddress;
    }
    public void setCvu(String aCVU) {
        this.cvu = aCVU;
    }
    public int getPoints(){ return this.points; }

    public List<Intention> getOffers() { return this.offers;}

    public void offer(Intention intention, BigDecimal currentPrice){
        new ValidatorCryptoPrice().checkIntention(intention, currentPrice);
        this.addIntention(intention);
    }

    public void accept(Intention anIntention, BigDecimal aCurrentPrice){
        if(this.isSameUser(anIntention.getOffered())){
            throw new IncorrectUserException(this.id);
        }
        anIntention.verifyIfIsAcepted(aCurrentPrice);
        if (anIntention.getStatus().equals(Status.SOLD)) {
            addIntention(anIntention);
        }
    }

    public void cancel(Intention intention) {
        if(intention.isItsOfferer(this) || intention.isItsDemander(this) ) {
            intention.cancel(this);
        }
    }

    public int quantityIntentionsSold() { return ((int) this.intentionWithStatus(Status.SOLD).count());}

    public int getReputation() {
        try{
            return this.points / this.quantityIntentionsSold();
        }
        catch(ArithmeticException ex){
            return 0;
        }
    }

    public void addPoints(int reward) {
        this.points += reward;
    }

    public boolean isSameUser(User otherUser) {
        return this.walletAddress.equals(otherUser.getWalletAddress())
                && this.email.equals(otherUser.getEmail())
                && this.cvu.equals(otherUser.getCvu());
    }

    public void applyPenalty(int i) {
        this.points -= i;
    }

    public void addIntention(Intention intention) {
        this.offers.add(intention);
    }

    public Stream<Intention> activatedIntentions() {
        return this.intentionWithStatus(Status.OFFERED);
    }

    private Stream<Intention> intentionWithStatus(Status aStatus){
        return this.offers.stream().filter(intention -> intention.hasStatus(aStatus));
    }

    public List<Intention> offersBetween(Date init, Date end) {
        return this.offers.stream().filter(intention -> intention.isBetween(init, end)).toList();
    }

    public void delivery(Intention intention) {
        intention.deliveryDone();
    }

    public void payment(Intention intention) {
        intention.transferDone();
    }
}
