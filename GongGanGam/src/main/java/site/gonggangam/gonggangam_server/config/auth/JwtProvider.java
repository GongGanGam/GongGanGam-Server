package site.gonggangam.gonggangam_server.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private static final long TOKEN_VALIDATION_TIME = 1000L * 60 * 30; // 30 minutes
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 24 * 60; // 60 days

    private final AccountDetailService accountDetailService;
    private final JwtConfig jwtConfig;
    private final JWTVerifier tokenValidator;

    public Long getUserIdFromToken(String token) throws JWTVerificationException {
        DecodedJWT verifiedToken = validateToken(token);
        return verifiedToken.getClaim("userId").asLong();
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        return tokenValidator.verify(token);
    }

    public String generateAccessToken(Map<String, String> payload) {
        return generateToken(TOKEN_VALIDATION_TIME, payload);
    }

    public String generateRefreshToken(Map<String, String> payload) {
        return generateToken(REFRESH_TOKEN_VALIDATION_TIME, payload);
    }

    public String generateToken(long expireTime, Map<String, String> payload) {
        long now = System.currentTimeMillis();

        return JWT.create()
                .withIssuer(jwtConfig.issuer)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + expireTime))
                .withPayload(payload)
                .sign(jwtConfig.getSigningKey(jwtConfig.secretKey));
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccountDetails accountDetails = (AccountDetails) accountDetailService.loadUserByUsername(authentication.getName());

        return new UsernamePasswordAuthenticationToken(
                accountDetails,
                "",
                accountDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
