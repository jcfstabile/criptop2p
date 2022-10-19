package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

public class UserInfoDTO {
    private final Long id;
    private final String name;
    private final String surname;
    private final int operationCount;
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
