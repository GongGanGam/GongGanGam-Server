package site.gonggangam.gonggangam_server.config.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.gonggangam.gonggangam_server.config.HttpServletUtils;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.service.OAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER_EXCEPTION = "jwt_exception";

    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(JwtProvider.ACCESS_TOKEN_HEADER);

        try {
            AccessTokenClaims accessTokenClaims = jwtProvider.getClaimsFromAccessToken(token);
            Authentication authentication = oAuthService.authenticateByUsername(accessTokenClaims.getIdentification());
            HttpServletUtils.setUserId(request, accessTokenClaims.getId());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (GeneralException ex) {
            delegateError(request, ex.getErrorCode());
        } catch (AuthenticationException ex) {
            delegateError(request, ResponseCode.AUTHENTICATION_INVALID_USER);
        }

        filterChain.doFilter(request, response);
    }

    private void delegateError(HttpServletRequest request, ResponseCode errCode) throws IOException {
        request.setAttribute(HEADER_EXCEPTION, errCode);
    }
}
