package site.gonggangam.gonggangam_server.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.Users;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String ACCESS_TOKEN_HEADER = "accessToken";
    public static final String REFRESH_TOKEN_HEADER = "refreshToken";

    // TODO : 디벨롭환경이라 임시로 긴 만료시간
    private static final long TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 24 * 365;
//    private static final long TOKEN_VALIDATION_TIME = 1000L * 60 * 30; // 30 minutes
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 24 * 60; // 60 days

    private final JwtConfig jwtConfig;
    private final JWTVerifier tokenValidator;

    // TODO : social 별로 분리하기 (identification 겹칠 수 있음)
    public AccessTokenClaims getClaimsFromAccessToken(String token) throws GeneralException {
        DecodedJWT verifiedToken = validateToken(token);
        return AccessTokenClaims.builder()
                .id(verifiedToken.getClaim("id").asLong())
                .identification(verifiedToken.getClaim("identification").asString())
                .build();
    }

    public DecodedJWT validateToken(String token) throws GeneralException {
        if (token == null) throw new GeneralException(ResponseCode.TOKEN_IS_NULL);
        try {
            return tokenValidator.verify(token);
        } catch (TokenExpiredException ex) {
            throw new GeneralException(ResponseCode.TOKEN_EXPIRED);
        } catch (JWTDecodeException ex) {
            throw new GeneralException(ResponseCode.TOKEN_CANT_NOT_DECODE);
        } catch (JWTVerificationException ex) {
            throw new GeneralException(ResponseCode.TOKEN_INVALID);
        }
    }

    public String generateAccessToken(Users account) {
        return generateToken(TOKEN_VALIDATION_TIME, account);
    }

    public String generateRefreshToken(Users account) {
        return generateToken(REFRESH_TOKEN_VALIDATION_TIME, account);
    }

    public String generateToken(long expireTime, Users account) {
        long now = System.currentTimeMillis();

        return JWT.create()
                .withIssuer(jwtConfig.issuer)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + expireTime))
                .withClaim("id", account.getUserId())
                .withClaim("identification", account.getUsername())
                .withClaim("role", account.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .sign(jwtConfig.getSigningKey(jwtConfig.secretKey));
    }

}
