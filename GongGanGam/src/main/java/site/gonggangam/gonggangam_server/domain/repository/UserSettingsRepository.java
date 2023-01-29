package site.gonggangam.gonggangam_server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

}
