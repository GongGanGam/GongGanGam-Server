package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.notice.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByIsVisible(Boolean isVisible);
    Optional<Notice> findByNoticeIdAndIsVisible(Long noticeId, Boolean isVisible);
}
