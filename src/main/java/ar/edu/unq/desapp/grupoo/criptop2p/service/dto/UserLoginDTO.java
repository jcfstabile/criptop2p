package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

public class UserLoginDTO {
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private final String user;
    private final String password;

    public UserLoginDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
