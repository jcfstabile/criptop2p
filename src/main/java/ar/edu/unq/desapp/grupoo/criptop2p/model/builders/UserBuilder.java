package ar.edu.unq.desapp.grupoo.criptop2p.model.builders;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

public class UserBuilder {
    public String name;
    public String surname;
    public String email;
    public String address;
    public String password;
    public String walletAddress;
    public String cvu;

    public UserBuilder(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu) {
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;
        this.password = aPassword;
        this.walletAddress = aWalletAddress;
        this.cvu = aCvu;
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
