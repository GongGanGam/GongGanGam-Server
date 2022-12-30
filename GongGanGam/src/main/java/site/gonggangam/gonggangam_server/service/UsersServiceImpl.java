package site.gonggangam.gonggangam_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

//    private final UsersRepository usersRepository;
//
//    @Autowired
//    public UsersServiceImpl(UsersRepository userRepository) {
//        this.usersRepository = userRepository;
//    }
//
//    @Override
//    public Integer update(Integer userIdx, UsersRequestDto requestDto) {
//        Users users = usersRepository.findById(userIdx)
//                .orElseThrow(() -> new IllegalArgumentException(""));
//
//        users.update(requestDto.getNickname(), requestDto.getBirthYear(), requestDto.getShareType(), requestDto.getGender());
//        usersRepository.save(users);
//
//        return userIdx;
//    }
}
