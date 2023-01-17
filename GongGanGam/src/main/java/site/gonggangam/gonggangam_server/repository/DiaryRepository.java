package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.gonggangam.gonggangam_server.domain.diary.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Optional<Diary> findByDiaryIdAndIsVisible(Long diaryId, Boolean isVisible);
    List<Diary> findAllByIsVisible(Boolean isVisible);
    List<Diary> findAllByWriter_UserIdAndWritingDateIsAfterAndWritingDateIsBeforeAndIsVisible(Long userId, LocalDate after, LocalDate before, Boolean isVisible);
}
