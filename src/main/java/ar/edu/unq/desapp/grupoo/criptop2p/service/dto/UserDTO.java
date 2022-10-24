package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDTO {
    @Schema(example = "1")
    private final Long id;
    @Schema(example = "Ana")
    private final String name;
    @Schema(example = "Poe")
    private final String surname;
    @Schema(example = "ana.poe@host.net")
    private final String email;
    @Schema(example = "Roque Sáens Peña 352, Bernal")
    private final String address;
    @Schema(example = "ABC12345")
    private final String walletAddress;
    @Schema(example = "0000054321000007654321" )
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
