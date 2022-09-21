package ar.edu.unq.desapp.grupoo.criptop2p.model.builders;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

public class UserBuilder {
    String name;
    String surname;
    String email;
    String address;
    String password;
    String walletAddress;
    String cvu;

    public UserBuilder() {
        this.name = "aaa";
        this.surname = "bbb";
        this.email = "c@d.e";
        this.address = "fghijklmno";
        this.password = "Pqrs7$";
        this.walletAddress = "tuvwxyzA";
        this.cvu = "1234567890123456789012";
    }

     public UserBuilder withName(String aName) {
        this.name = aName;
        return this;
    }

    public UserBuilder withSurname(String aSurname) {
        this.surname = aSurname;
        return this;
    }

    public UserBuilder withEmail(String aEmail) {
        this.email = aEmail;
        return this;
    }

    public UserBuilder withAddress(String aAddress) {
        this.address = aAddress;
        return this;
    }

    public UserBuilder withPassword(String aPassword) {
        this.password = aPassword;
        return this;
    }

    public UserBuilder withWalletAddress(String aWalletAddress) {
        this.walletAddress = aWalletAddress;
        return this;
    }

    public UserBuilder withCvu(String aCvu) {
        this.cvu = aCvu;
        return this;
    }

    public User build() {
        return new User(this.name, this.surname, this.email, this.address, this.password, this.walletAddress, this.cvu);
    }
}
