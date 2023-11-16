package com.example.trip_itinerary.member.jwt;

import com.example.trip_itinerary.member.exception.LoginCredentialExpired;
import com.example.trip_itinerary.member.exception.MemberErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.key}")
    private String SECRET_KEY;

    private final UserDetailsService userDetailsService;
    private final long tokenValidTime = 30 * 60 * 1000L;

    @PostConstruct
    public void init() {
        SECRET_KEY = Base64.getEncoder()
                .encodeToString(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public UsernamePasswordAuthenticationToken validateToken(String token) {
        Date now = new Date();
        if (getExpiryDateFromJWT(token).before(now)) {
            throw new LoginCredentialExpired(MemberErrorCode.LOGIN_CREDENTIAL_EXPIRED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromJWT(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }


    public Date getExpiryDateFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

}
