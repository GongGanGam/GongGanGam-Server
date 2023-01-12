package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.ProviderType;
import site.gonggangam.gonggangam_server.domain.users.types.Role;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;

public interface UsersService {

    Users createUser(UsersRequestDto.PostUser request, String email, ProviderType providerType, Role role);

    void updateInfo(Long userId, UsersRequestDto.PutUserInfo request);

    void updateSettings(Long userId, UserSettingsRequestDto request);

}
