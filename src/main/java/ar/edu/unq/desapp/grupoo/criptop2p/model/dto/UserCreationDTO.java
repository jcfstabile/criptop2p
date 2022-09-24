package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

import javax.validation.constraints.Size;

public class UserCreationDTO {
    @Size(min= 3, max=30)
    private final String name;
    @Size(min= 3, max=30)
    private final String surname;
    private final String email;
    @Size(min= 10, max=30)
    private final String address;
    @Size(min= 6)
    private final String password;
    @Size(min= 8, max =8)
    private final String walletAddress;
    @Size(min= 22, max =22)
    private final String cvu;

    public UserCreationDTO(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;
        this.password = aPassword;
        this.walletAddress = aWalletAddress;
        this.cvu = aCvu;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getPassword() {
        return password;
    }
    public String getWalletAddress() {
        return walletAddress;
    }
    public String getCvu() {
        return cvu;
    }
}
