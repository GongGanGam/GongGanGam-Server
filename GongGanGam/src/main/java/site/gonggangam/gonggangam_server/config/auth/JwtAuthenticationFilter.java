package site.gonggangam.gonggangam_server.config.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(JwtProvider.ACCESS_TOKEN_HEADER);

        try {
            jwtProvider.validateToken(token);
            String email = jwtProvider.getEmailFromToken(token);
            Authentication authentication = jwtProvider.authenticate(new UsernamePasswordAuthenticationToken(email, ""));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenExpiredException ex) {
            resolver.resolveException(request, response, null, new GeneralException(ResponseCode.TOKEN_EXPIRED));
        } catch (JWTDecodeException ex) {
            resolver.resolveException(request, response, null, new GeneralException(ResponseCode.TOKEN_CANT_NOT_DECODE));
        } catch (JWTVerificationException ex) {
            resolver.resolveException(request, response, null, new GeneralException(ResponseCode.TOKEN_INVALID));
        } catch (Exception ex) {
            resolver.resolveException(request, response, null, ex);
        }

        super.doFilter(request, response, filterChain);
    }
}
