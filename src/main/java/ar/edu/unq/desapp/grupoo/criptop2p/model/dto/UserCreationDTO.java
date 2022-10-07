package ar.edu.unq.desapp.grupoo.criptop2p.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserCreationDTO {
    @Schema(minLength = 3, maxLength = 30, example = "Ana")
    private final String name;
    @Schema(minLength = 3, maxLength = 30, example = "Poe")
    private final String surname;
    @Schema(example = "ana.poe@host.net")
    private final String email;
    @Schema(minLength = 10, maxLength = 30, example = "Roque Sáens Peña 352, Bernal")
    private final String address;
    @Schema(minLength = 6, example = "Ab%$12")
    private final String password;
    @Schema(minLength = 8, maxLength = 8, example = "ABC12345")
    private final String walletAddress;
    @Schema(minLength = 22, maxLength = 22, example = "0000054321000007654321" )
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
