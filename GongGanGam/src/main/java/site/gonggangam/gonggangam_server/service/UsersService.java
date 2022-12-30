package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;

public interface UsersService {

    Users create(UsersRequestDto.Post request, String email, AuthType authType);

    void updateInfo(Long userId, UsersRequestDto.PatchInfo request);

    void updateProfImg(Long userId, UsersRequestDto.PatchProfImg request);

}
