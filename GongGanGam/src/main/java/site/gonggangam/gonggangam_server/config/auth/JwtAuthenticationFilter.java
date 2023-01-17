package site.gonggangam.gonggangam_server.config.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.OAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(JwtProvider.ACCESS_TOKEN_HEADER);

        try {
            jwtProvider.validateToken(token);
            String email = jwtProvider.getEmailFromToken(token);
            Authentication authentication = oAuthService.authenticate(new UsernamePasswordAuthenticationToken(email, ""));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenExpiredException ex) {
            setErrorResponse(response, ResponseCode.TOKEN_EXPIRED);
        } catch (JWTDecodeException ex) {
            setErrorResponse(response, ResponseCode.TOKEN_CANT_NOT_DECODE);
        } catch (JWTVerificationException | AuthenticationException ex) {
            setErrorResponse(response, ResponseCode.TOKEN_INVALID);
        } catch (GeneralException ex) {
            setErrorResponse(response, ex.getErrorCode());
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ResponseCode errCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), ErrorResponseDto.of(errCode));
    }
}
