package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.security.TokenUtils;
import ar.edu.unq.desapp.grupoo.criptop2p.security.UserDetailsImpl;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    public String authenticateUser(UserLoginDTO userLoginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUser(), userLoginDTO.getPassword(), Collections.emptyList());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return "Bearer " + TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());
    }
}
