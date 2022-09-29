package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

public class UserCreationDTO {
    private final String name;
    private final String surname;
    private final String email;
    private final String address;
    private final String password;
    private final String walletAddress;
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
