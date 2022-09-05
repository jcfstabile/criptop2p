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
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public
    Long id;

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

    @Column(name="w", nullable = false, unique = true)
    String w;

    @Column(name="cvu", nullable = false)
    String cvu;
    public User(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;
        this.password = aPassword;
        this.w = aWalletAddress;
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
}
