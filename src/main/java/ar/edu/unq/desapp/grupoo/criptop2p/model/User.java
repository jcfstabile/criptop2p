package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public void setPoints(int newPoints){
        this.points = newPoints;
    }
    public List<Intention> getOffers() { return this.offers;}

    public Intention offer(Integer aCount, BigDecimal aPrice, TypeIntention aType, CryptoName aCryptoName, BigDecimal currentPrice){
        Intention intention = new ValidatorCryptoPrice().createIntention(this, aCount, aPrice, aType, aCryptoName, currentPrice);
        this.addIntention(intention);
        return intention;
    }

    public void accept(Intention anIntention, BigDecimal aCurrentPrice) {
        anIntention.verifyIfIsAcepted(aCurrentPrice);
    }

    public void cancel(Intention intention) {
        intention.cancel(this);
    }

    public int quantityIntentions() { return ((int) this.offers.stream().filter(intention -> intention.hasStatus(Status.SOLD)).count());}

    public int getReputation() {
        try{
            return this.points / this.quantityIntentions();
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
}
