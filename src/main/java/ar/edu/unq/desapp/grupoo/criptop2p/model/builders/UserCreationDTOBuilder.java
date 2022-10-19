package ar.edu.unq.desapp.grupoo.criptop2p.model.builders;

import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;

public class UserCreationDTOBuilder {
    protected String name;
    protected String surname;
    protected String email;
    protected String address;
    protected String password;
    protected String walletAddress;
    protected String cvu;

    public UserCreationDTOBuilder(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu) {
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;

        this.password = aPassword;
        this.walletAddress = aWalletAddress;
        this.cvu = aCvu;
    }

     public UserCreationDTOBuilder withName(String aName) {
        this.name = aName;
        return this;
    }

    public UserCreationDTOBuilder withSurname(String aSurname) {
        this.surname = aSurname;
        return this;
    }

    public UserCreationDTOBuilder withEmail(String aEmail) {
        this.email = aEmail;
        return this;
    }

    public UserCreationDTOBuilder withAddress(String aAddress) {
        this.address = aAddress;
        return this;
    }

    public UserCreationDTOBuilder withPassword(String aPassword) {
        this.password = aPassword;
        return this;
    }

    public UserCreationDTOBuilder withWalletAddress(String aWalletAddress) {
        this.walletAddress = aWalletAddress;
        return this;
    }

    public UserCreationDTOBuilder withCvu(String aCvu) {
        this.cvu = aCvu;
        return this;
    }

    public UserCreationDTO build() {
        return new UserCreationDTO(this.name, this.surname, this.email, this.address, this.password, this.walletAddress, this.cvu);
    }
}
