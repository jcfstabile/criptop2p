package ar.edu.unq.desapp.grupoo.criptop2p.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("JWTAuthenticator Test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JWTAuthenticatorFilterTest {

    JWTAuthenticatorFilter jwtAuthenticatorFilter;
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void setUp(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        jwtAuthenticatorFilter = new JWTAuthenticatorFilter();
    }

    @DisplayName("Attempt an authentication throw exception")
    @Test
    void attemptAuthentication() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(request.getReader()).thenReturn(reader);

        assertThrows(RuntimeException.class, () -> {
                jwtAuthenticatorFilter.attemptAuthentication(request, response);
        });

        verify(request, atLeast(1)).getReader();
    }

    @DisplayName("Success authentication")
    @Test
    void successfulAuthentication() throws ServletException, IOException {
        FilterChain filterChain = mock(FilterChain.class);
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl udi = mock(UserDetailsImpl.class);
        PrintWriter pw = mock(PrintWriter.class);
        when(authentication.getPrincipal()).thenReturn(udi);
        when(response.getWriter()).thenReturn(pw);

        jwtAuthenticatorFilter.successfulAuthentication(request, response, filterChain, authentication);

        verify(authentication, atLeastOnce()).getPrincipal();
        verify(udi, atLeastOnce()).getName();
        verify(udi, atLeastOnce()).getUsername();
        verify(response, atLeastOnce()).addHeader(anyString(), anyString());
        verify(response, atLeastOnce()).getWriter();
    }
}