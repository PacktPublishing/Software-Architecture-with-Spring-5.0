package com.packtpub.bankingapplication.security.service

import com.packtpub.bankingapplication.security.domain.JwtUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.util.concurrent.TimeUnit

@Service
public class JwtService {

    private Long expireHours = 24

    @Value('${jwt-token-secret}')
    private String plainSecret

    private String encodedSecret

    @PostConstruct
    void init(){
        encodedSecret = Base64.getEncoder().encodeToString(this.plainSecret.getBytes())
    }

    JwtUser getUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token).getBody()
        String username = claims.getSubject()
        String role = (String) claims.get("role")
        return new JwtUser(username: username, role: role)
    }

    String getToken(JwtUser jwtUser) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(jwtUser.username)
                .claim("role", jwtUser.role)
                .setIssuedAt(new Date())
                .setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact()
    }

    protected Date getExpirationTime() {
        Date now = new Date()
        Long expireInMs = TimeUnit.HOURS.toMillis(expireHours)
        return new Date(expireInMs + now.getTime())
    }


}