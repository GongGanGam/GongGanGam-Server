package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.user_settings.UserSettings;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

}
