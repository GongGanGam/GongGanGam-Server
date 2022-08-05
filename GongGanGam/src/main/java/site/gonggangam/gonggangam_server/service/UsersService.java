package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.dto.users.PatchUsersRequestDto;

public interface UsersService {

    Integer update(Integer userIdx, PatchUsersRequestDto requestDto);

}
