package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;


public class UserDTO {
    private final Long id;
    private final String name;
    private final String surname;
    private final String email;
    private final String address;
    private final String walletAddress;
    private final String cvu;

    public UserDTO(Long anId, String aName, String aSurname, String anEmail, String anAddress, String aWalletAddress, String aCvu){
        this.id = anId;
        this.name = aName;
        this.surname = aSurname;
        this.email = anEmail;
        this.address = anAddress;
        this.walletAddress = aWalletAddress;
        this.cvu = aCvu;
    }

    public Long getId() {
        return id;
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

    public String getWalletAddress() {
        return walletAddress;
    }

    public String getCvu() {
        return cvu;
    }
}
