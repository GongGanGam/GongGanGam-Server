package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.controller.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.controller.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.controller.dto.users.UsersResponseDto;

public interface UsersService {

    UsersResponseDto createUser(UsersRequestDto.PostUser request, Long userId) throws GeneralException;
    UsersResponseDto loadUser(String email) throws GeneralException;

    void updateInfo(Long userId, UsersRequestDto.PutUserInfo request) throws GeneralException;

    void updateSettings(Long userId, UserSettingsRequestDto request) throws GeneralException;

}
