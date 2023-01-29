package site.gonggangam.gonggangam_server.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiary;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiaryPK;

import java.util.List;

public interface ShareDiaryRepository extends JpaRepository<ShareDiary, ShareDiaryPK> {

    @Query(value = """
                        SELECT sd
                        FROM ShareDiary sd
                        WHERE sd.receiver.userId = :userId
                        AND
                        sd.diary.isVisible = true
                        AND
                        sd.diary.shareAgreed = true
            """)
    Page<ShareDiary> findByReceiverUserId(@Param("userId") Long userId, Pageable pageable);
}
