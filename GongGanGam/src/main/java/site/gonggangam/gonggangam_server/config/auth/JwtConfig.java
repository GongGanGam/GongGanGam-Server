package site.gonggangam.gonggangam_server.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    public final String secretKey;
    public final String issuer;

    public JwtConfig(@Value("${jwt.secret}") String secretKey, @Value("${jwt.issuer}") String issuer) {
        this.secretKey = secretKey;
        this.issuer = issuer;
    }

    @Bean
    public JWTVerifier tokenValidator() {
        return JWT.require(getSigningKey(secretKey))
                .withIssuer(issuer)
                .build();
    }

    public Algorithm getSigningKey(String secretKey) {
        return Algorithm.HMAC256(secretKey);
    }

}
