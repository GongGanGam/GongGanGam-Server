package site.gonggangam.gonggangam_server.config.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    private final HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info("authentication 실패");
//        resolver.resolveException(request, response, null, authException);
    }

}
