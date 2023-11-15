package com.example.trip_itinerary.member.jwt;

import com.example.trip_itinerary.member.exception.LoginCredentialExpired;
import com.example.trip_itinerary.member.exception.MemberErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    //    @Value("asgasdas")
//    private String jwtSecret = "Asdasfbahjsdbajsbfjbajbshjdbasbjfbashjbduqwybeqwjhbdfjbqwhjf";
//    private static final String SECRET_KEY = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";
    private final UserDetailsService userDetailsService;

    @Value("${jwt.key}")
    private String secretKey;
    private long tokenValidTime = 30 * 60 * 1000L;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public UsernamePasswordAuthenticationToken validateToken(String token) {
        Date now = new Date();
        if(getExpiryDateFromJWT(token).before(now)){
            throw new LoginCredentialExpired(MemberErrorCode.LOGIN_CREDENTIAL_EXPIRED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromJWT(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }


    public Date getExpiryDateFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

}
