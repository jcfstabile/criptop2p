package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Table(name="intentions")
@Validated
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
    private Long price;
    private Crypto crypto;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Intention() {
    }

    public Intention(User anUser, int aCount, Long aPrice, Crypto aCrypto) {
        this.count = aCount;
        this.price= aPrice;
        this.crypto = aCrypto;
    }

    public int getCount(){ return this.count; }
    public Long getPrice(){ return this.price; }
    public Crypto getCrypto(){ return this.crypto; }
    public User getUser(){ return this.user; }
}
