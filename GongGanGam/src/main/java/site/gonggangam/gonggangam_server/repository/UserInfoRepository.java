package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.users.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
