package ar.edu.unq.desapp.grupoo.criptop2p.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

public class TokenUtils {
    
    private static final String ACCESS_TOKEN_SECRET = "secret_token_eca1c4De7nEc0D50515";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = (long) 7 * 24 * 60 * 60;

    private TokenUtils(){}

    public static String createToken(String name, String email){
        var expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        var expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) { return null; }
    }
}
