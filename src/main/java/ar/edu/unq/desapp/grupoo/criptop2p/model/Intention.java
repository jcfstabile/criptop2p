package ar.edu.unq.desapp.grupoo.criptop2p.model;

import javax.persistence.*;

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
    private Long price;

    private Type type;
    private CryptoName cryptoName;

    private Status status;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;


    public Intention() {
    }

    public Intention(User anUser, int aCount, Long aPrice, Type aType, CryptoName aCryptoName) {
        this.user=anUser;
        this.count = aCount;
        this.price= aPrice;
        this.type = aType;
        this.cryptoName = aCryptoName;
        this.status = Status.OFFERED;
    }
    public User getUser(){ return this.user; }
    public int getCount(){ return this.count; }
    public Long getPrice(){ return this.price; }
    public Type getType(){ return this.type; }
    public CryptoName getCrypto(){ return this.cryptoName; }
    public Status getStatus() { return this.status; }
    public void setStatus(Status aStatus){ this.status = aStatus; }
}
