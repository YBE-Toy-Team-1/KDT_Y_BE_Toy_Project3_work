package com.example.trip_itinerary.member.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

public class JwtTokenProvider {
    @Value("asgasdas")
    private String jwtSecret;
    private int jwtExpirationTime = 60;

    @Autowired
    private UserDetailsService userDetailsService;

    public String generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }


    // 토큰의 유효성을 검증하는 메소드
    public UsernamePasswordAuthenticationToken validateToken(String token) {
        Date now = new Date();
        if(getExpiryDateFromJWT(token).before(now)){
            throw new RuntimeException("토큰 만료됨");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromJWT(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "");
    }

    private String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // 토큰에서 만료 시간을 추출하는 메소드
    public Date getExpiryDateFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

}
