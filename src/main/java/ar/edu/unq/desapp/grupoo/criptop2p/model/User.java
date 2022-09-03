package ar.edu.unq.desapp.grupoo.criptop2p.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="user")
public class User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public
    Integer id;

    @Column(name="name", nullable = false)
    public
    String name;

    @Column(name="surname", nullable = false)
    String surname;

    @Column(name="email", nullable = false, unique = true)
    public
    String email;

    @Column(name="address", nullable = false)
    String address;

    @Column(name="password", nullable = false)
    String password;

    @Column(name="walletAddress", nullable = false, unique = true)
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
}
