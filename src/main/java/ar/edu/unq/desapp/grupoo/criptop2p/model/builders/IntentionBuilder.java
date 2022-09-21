package ar.edu.unq.desapp.grupoo.criptop2p.model.builders;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Type;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

import java.math.BigDecimal;

public class IntentionBuilder {
    User user;
    int count;
    BigDecimal price;
    Type type;
    CryptoName crypto;

    public IntentionBuilder() {
        this.user = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
        this.count = 1;
        this.price = new BigDecimal(2);
        this.type = Type.SELL;
        this.crypto = CryptoName.ALICEUSDT;
    }

    public IntentionBuilder withCount(int count) {
        this.count = count;
        return this;
    }

    public IntentionBuilder withPrice(double price) {
        this.price = new BigDecimal(price);
        return this;
    }

    public IntentionBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public IntentionBuilder withType(Type type) {
        this.type = type;
        return this;
    }

    public IntentionBuilder withCrypto(CryptoName crypto) {
        this.crypto = crypto;
        return this;
    }

    public Intention build() {
        return new Intention(this.user, this.count, this.price, this.type, this.crypto);
    }
}
