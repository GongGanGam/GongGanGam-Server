package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.user_settings.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.Users;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Users> {

}
