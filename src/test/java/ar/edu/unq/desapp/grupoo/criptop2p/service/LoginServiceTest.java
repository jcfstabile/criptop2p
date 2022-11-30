package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.security.TokenUtils;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.services.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @DisplayName("When existent user login got a bearer jwt token")
    @Test
    void testLoginExistentUser(){
        var userLoginDTO = new UserLoginDTO("homero@init.data", "Homero1234.");

        var bearerToken = loginService.authenticateUser(userLoginDTO);

        assertTrue(bearerToken.startsWith("Bearer "));
    }

    @DisplayName("When existent user authenticate its returned token load include its user name")
    @Test
    void testJwtLoadIncludeUserName(){
        var userLoginDTO = new UserLoginDTO("homero@init.data", "Homero1234.");

        var jwtToken = loginService.authenticateUser(userLoginDTO).replace("Bearer ", "");

        var userInfo = TokenUtils.getAuthentication(jwtToken);

        assertNotNull(userInfo);
        assertEquals("homero@init.data", userInfo.getName());
    }

    @DisplayName("When non existent user authenticate throws exception")
    @Test
    void testUserNotExist(){
        var userLoginDTO = new UserLoginDTO("nonExist@init.data", "Aa1234.!");


        var badCredentialsException = assertThrows(BadCredentialsException.class, () ->
                        loginService.authenticateUser(userLoginDTO)
                );

        assertEquals("Bad credentials", badCredentialsException.getMessage());
    }


}