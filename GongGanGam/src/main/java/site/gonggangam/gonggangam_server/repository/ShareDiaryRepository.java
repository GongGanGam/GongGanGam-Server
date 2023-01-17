package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiary;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiaryPK;

import java.util.List;

public interface ShareDiaryRepository extends JpaRepository<ShareDiary, ShareDiaryPK> {

    List<ShareDiary> findAllByReceiver_UserIdAndDiary_IsVisible(Long userId, Boolean isVisible);
}
