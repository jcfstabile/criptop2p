package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserLoginDTO {
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Schema(example = "homersimpsons@bomb.uk")
    private final String user;

    @Schema(example = "AGoodPassword.4321")
    private final String password;

    public UserLoginDTO(String email, String password) {
        this.user = email;
        this.password = password;
    }
}
