package com.lcaohoanq.shoppe.component;

import com.lcaohoanq.shoppe.exception.InvalidParamException;
import com.lcaohoanq.shoppe.exception.JwtAuthenticationException;
import com.lcaohoanq.shoppe.domain.token.Token;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.token.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtils {

    private final TokenRepository tokenRepository;
    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    @Value("${jwt.secretKey}")
    private String secretKey;

    //    private final TokenRepository tokenRepository;
    public String generateToken(User user) throws Exception {
        //properties => claims
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        try {
            //how to extract claims from this ?
            return Jwts.builder()
                .setClaims(claims) //how to extract claims from this ?
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        } catch (Exception e) {
            //you can "inject" Logger, instead System.out.println
            throw new InvalidParamException("Cannot create jwt token, error: " + e.getMessage());
            //return null;
        }
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="));
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        return Encoders.BASE64.encode(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, User userDetails) {
        try {
            String email = extractEmail(token);
            Token existingToken = tokenRepository.findByToken(token);
            
            // Check token existence and revocation
            if (existingToken == null || existingToken.isRevoked()) {
                throw new JwtAuthenticationException("Token is invalid or has been revoked");
            }

            // Check token matches user
            if (!email.equals(userDetails.getUsername())) {
                throw new JwtAuthenticationException("Token does not match user");
            }

            // Check expiration
            if (isTokenExpired(token)) {
                throw new ExpiredJwtException(null, null, "Token has expired");
            }

            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new JwtAuthenticationException("Invalid JWT token format");
        } catch (UnsupportedJwtException e) {
            throw new JwtAuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT claims string is empty");
        }
    }
}
