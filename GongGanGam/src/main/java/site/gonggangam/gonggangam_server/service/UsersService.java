package site.gonggangam.gonggangam_server.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.ProviderType;
import site.gonggangam.gonggangam_server.domain.users.types.Role;
import site.gonggangam.gonggangam_server.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.dto.auth.OAuthResponseDto;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersResponseDto;

public interface UsersService {

    UsersResponseDto createUser(UsersRequestDto.PostUser request, Long userId) throws GeneralException;
    UsersResponseDto loadUser(String email) throws GeneralException;

    void updateInfo(Long userId, UsersRequestDto.PutUserInfo request) throws GeneralException;

    void updateSettings(Long userId, UserSettingsRequestDto request) throws GeneralException;

}
