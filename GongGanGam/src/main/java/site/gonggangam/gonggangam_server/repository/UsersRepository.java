package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.users.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByIdentification(String identification);
}
