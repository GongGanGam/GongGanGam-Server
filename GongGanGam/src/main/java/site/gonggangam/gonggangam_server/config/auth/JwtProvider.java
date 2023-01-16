package site.gonggangam.gonggangam_server.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.service.OAuthService;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    public static final String ACCESS_TOKEN_HEADER = "accessToken";
    public static final String REFRESH_TOKEN_HEADER = "refreshToken";

    private static final long TOKEN_VALIDATION_TIME = 1000L * 60 * 30; // 30 minutes
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 24 * 60; // 60 days

    private final OAuthService oAuthService;
    private final JwtConfig jwtConfig;
    private final JWTVerifier tokenValidator;

    public String getEmailFromToken(String token) throws JWTVerificationException {
        DecodedJWT verifiedToken = validateToken(token);
        return verifiedToken.getClaim("email").asString();
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException, GeneralException {
        if (token == null) throw new GeneralException(ResponseCode.TOKEN_IS_NULL);
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, GeneralException {
        Users user = (Users) oAuthService.loadUserByUsername(authentication.getName());

        return new UsernamePasswordAuthenticationToken(
                user,
                "",
                user.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
