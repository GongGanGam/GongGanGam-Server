package site.gonggangam.gonggangam_server.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.gonggangam.gonggangam_server.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.dto.auth.OAuthResponseDto;

public interface OAuthService extends AuthenticationProvider {

    Authentication authenticateByUsername(String username);
    OAuthResponseDto kakaoLogin(OAuthRequestDto request);
}
