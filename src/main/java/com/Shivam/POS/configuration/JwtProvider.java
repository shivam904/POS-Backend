package com.Shivam.POS.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    static SecretKey key= Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication authentication){
        Collection<? extends GrantedAuthority>authorities=authentication.getAuthorities();
        String roles=populateAuthorities(authorities);
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
    }
    public String getEmailFromToken(String token){
        token= token.substring(7);
        Claims claims= Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return String.valueOf(claims.get("email"));
    }
    private String populateAuthorities(Collection<? extends GrantedAuthority> auth){
        Set<String> auths= new HashSet<>();
        for(GrantedAuthority authority: auth){
            auths.add(authority.getAuthority());

        }
        return String.join(",",auths);

    }
}
