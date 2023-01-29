package site.gonggangam.gonggangam_server.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.notice.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByIsVisible(Boolean isVisible);
    Page<Notice> findByIsVisible(Boolean isVisible, Pageable pageable);
    Optional<Notice> findByNoticeIdAndIsVisible(Long noticeId, Boolean isVisible);
}
