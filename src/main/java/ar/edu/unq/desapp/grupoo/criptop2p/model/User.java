package ar.edu.unq.desapp.grupoo.criptop2p.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="surname", nullable = false)
    String surname;

    @Column(name="email", nullable = false, unique = true)
    String email;

    @Column(name="address", nullable = false)
    String address;

    @Column(name="password", nullable = false)
    String password;

    @Column(name="w", nullable = false, unique = true)
    String walletAddress;

    @Column(name="cvu", nullable = false)
    String cvu;

    public User(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;
        this.password = aPassword;
        this.walletAddress = aWalletAddress;
        this.cvu = aCvu;
    }

    public User() {

    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long anId){
        this.id = anId;
    }

    public String getName() { return this.name; }
    public String getSurname() { return this.surname; }
    public String getEmail() { return this.email; }
    public String getAddress() { return this.address; }
    public String getPassword() { return this.password; }
    public String getWalletAddress() { return this.walletAddress; }
    public String getCvu() { return this.cvu; }

}
