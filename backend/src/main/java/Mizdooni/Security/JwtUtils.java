package Mizdooni.Security;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.*;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;

public class JwtUtils {
    private static final String SECRET_KEY = "mizdooni2024";

    private static Date expirationDate() {
        long curTime = System.currentTimeMillis();
        long EXPIRE_PERIOD = 24 * 60 * 60 * 1000;
        return new Date(curTime + (EXPIRE_PERIOD));
    }

    public static String createJWT(String username) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder();
        builder.setIssuedAt(new Date(System.currentTimeMillis()));
        builder.setExpiration(expirationDate());
        builder.setAudience(username);
        builder.signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public static String verifyJWT(String jwt) {
        if(jwt.charAt(0) != 'e') jwt = jwt.split(" ", 2)[1];
        System.out.println("verify:" + jwt);
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();

        if(claims.getIssuedAt() == null ||
                claims.getExpiration()== null) {
            return null;
        }
        if (claims.getExpiration().getTime() < System.currentTimeMillis())
            return null;
        return claims.getAudience();
    }
}
