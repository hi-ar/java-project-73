package hexlet.code.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {

    private Logger log = LoggerFactory.getLogger(JWTUtils.class);
//    private String secret = "hexlet";
//    public String generateJwtToken(String email) {
//
//        Map<String, Object> claims = new HashMap<>();
//
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setSubject(email)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1)) //1 hour
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//
//        return token;
//    }

    @Autowired
    private JwtEncoder encoder;

    public String generateToken(String username) {
        Instant currentMoment = Instant.now();
        //JWT Claims Set это JSON объект представляющий утверждения передаваемые JSON Web Token.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(currentMoment)
                .expiresAt(currentMoment.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .build();

        Jwt jwt = this.encoder.encode(JwtEncoderParameters.from(claims));
        String token = jwt.getTokenValue();
        log.info("§§§§§ new token for correct login " + username + " -> " + token);
        return token;
    }
}
