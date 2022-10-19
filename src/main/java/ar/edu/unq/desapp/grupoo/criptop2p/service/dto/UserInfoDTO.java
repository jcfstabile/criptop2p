package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserInfoDTO {
    @Schema(example = "1")
    private final Long id;
    @Schema(example = "Ana")
    private final String name;
    @Schema(example = "Poe")
    private final String surname;
    @Schema(example = "5")
    private final int operationCount;
    @Schema(example = "21")
    private final int reputation;

    public UserInfoDTO(Long anId, String aName, String aSurname, int operationCount, int reputation) {
        this.id = anId;
        this.name = aName;
        this.surname = aSurname;
        this.operationCount = operationCount;
        this.reputation = reputation;
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

    public int getOperationCount() {
        return operationCount;
    }

    public int getReputation() {
        return reputation;
    }
}
