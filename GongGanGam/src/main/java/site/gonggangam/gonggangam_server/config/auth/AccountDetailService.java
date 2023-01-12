package site.gonggangam.gonggangam_server.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.dto.ResponseCode;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws GeneralException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new GeneralException(ResponseCode.NOT_FOUND_USER);
                });

        return new AccountDetails(user);
    }
}
