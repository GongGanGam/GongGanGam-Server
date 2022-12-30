package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.users.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
