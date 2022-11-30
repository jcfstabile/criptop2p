package ar.edu.unq.desapp.grupoo.criptop2p.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@DisplayName("JWTAuthorizationFilter Test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JWTAuthorizationFilterTest {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @DisplayName("doFilterInternal get Authorized header and go to next filter in chain")
    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer x");

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(request, atLeastOnce()).getHeader("Authorization");
        verify(filterChain, atLeast(1)).doFilter(request, response);

    }
}