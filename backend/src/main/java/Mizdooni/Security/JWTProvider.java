package Mizdooni.Security;

import io.jsonwebtoken.*;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
//
//@Component
//@Slf4j
//public class JWTProvider {
//    private static final String SECRET = "Baloot2023";
//
//    private static Key getKey() {
//        byte[] keyBytes = DatatypeConverter.parseBase64Binary(SECRET + SECRET + SECRET + SECRET + SECRET);
//        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    public boolean validate(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
//            return true;
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            log.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//    }
//
//    public String generateToken(Authentication authentication) {
//        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails);
//    }
//
//    private String createToken(Map<String, Object> claims, UserPrincipal userDetails) {
//        claims.put("userEmail", userDetails.getEmail());
//        return Jwts.builder()
//                .setIssuer("baloot")
//                .setIssuedAt(Date.from(Instant.now()))
//                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
//                .setSubject(userDetails.getUsername())
//                .addClaims(claims)
//                .signWith(getKey())
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpireDate(token).before(Date.from(Instant.now()));
//    }
//
//    private Date extractExpireDate(String token) {
//        return (Date) extractAllClaims(token).getExpiration();
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//}