package site.gonggangam.gonggangam_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.dto.users.PatchUsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    @Override
    public Integer update(Integer userIdx, PatchUsersRequestDto requestDto) {
        Users users = usersRepository.findById(userIdx)
                .orElseThrow(() -> new IllegalArgumentException(""));

        users.update(requestDto.getNickname(), requestDto.getBirthYear(), requestDto.getSetAge(), requestDto.getGender());
        usersRepository.save(users);

        return userIdx;
    }
}
