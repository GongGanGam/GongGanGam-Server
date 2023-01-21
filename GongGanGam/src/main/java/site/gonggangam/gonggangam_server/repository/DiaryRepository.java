package site.gonggangam.gonggangam_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.gonggangam.gonggangam_server.domain.diary.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query(value = """
                        SELECT d
                        FROM Diary d
                        WHERE d.isVisible = true
            """)
    Optional<Diary> getByDiaryId(@Param("diaryId") Long diaryId);

    @Query(value = """
                        SELECT d
                        FROM Diary d
                        WHERE d.isVisible = true
                        AND
                        d.writer.userId = :userId
                        AND
                        d.writingDate BETWEEN :start AND :end
            """)
    List<Diary> getByUserIdAndBetweenDate(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);



}
