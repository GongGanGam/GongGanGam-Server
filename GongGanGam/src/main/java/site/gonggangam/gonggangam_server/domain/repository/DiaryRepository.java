package site.gonggangam.gonggangam_server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    /**
     * 삭제되지 않은 일기를 id로 상세 정보 조회
     * @param diaryId 일기 id
     * @return 일기 정보
     */
    @Query(value = """
                        SELECT d
                        FROM Diary d
                        WHERE d.isVisible = true
                        AND
                        d.diaryId = :diaryId
            """)
    Optional<Diary> getByDiaryId(@Param("diaryId") Long diaryId);

    /**
     * 작성자와 날짜에 따른 일기 조회
     * @param userId 작성자 id
     * @param date 조회할 날짜
     * @return 조건에 해당하는 일기 목록
     */
    @Query(value = """
                        SELECT d
                        FROM Diary d
                        WHERE d.writer.userId = :userId
                        AND
                        d.writingDate = :date
                        AND
                        d.isVisible = true
            """)
    List<Diary> getByUserIdAndDate(@Param("userId") Long userId,
                                          @Param("date") LocalDate date);

    /**
     * 작성자와 날짜 범위에 따른 일기 조회
     * @param userId 작성자 id
     * @param start 조회 시작 시간
     * @param end 조회 끝 시간
     * @return 조건에 해당하는 일기 목록
     */
    @Query(value = """
                        SELECT d
                        FROM Diary d
                        WHERE d.writer.userId = :userId
                        AND
                        d.writingDate BETWEEN :start AND :end
                        AND
                        d.isVisible = true
            """)
    List<Diary> getByUserIdAndBetweenDate(@Param("userId") Long userId,
                                          @Param("start") LocalDate start,
                                          @Param("end") LocalDate end);

    /**
     * 사용자의 연령대 지정 설정과 실제로 일기를 작성한 시각에 따른 조회
     * <p>
     * 공유 허용된 일기만 조회됩니다.
     * <p>
     * ex) 비슷한 연령대 공유를 설정한 작성자들이 2023.01.01 21:00:01 ~ 2023.01.02 21:00:00에 작성한 일기 목록
     * @param shareType 공유 설정
     * @param dateStart 조회할 시작 시간
     * @param dateEnd 조회할 끝 시간
     * @return 조건에 해당하는 일기 목록과 작성자 정보
     */
    @Query(value = """
                        SELECT d
                        FROM Diary d
                        JOIN FETCH d.writer wr
                        JOIN FETCH wr.settings se
                        WHERE d.createdAt BETWEEN :dateStart AND :dateEnd
                        AND
                        d.shareAgreed = TRUE
                        AND
                        se.shareType = :shareType
                        AND
                        d.isVisible = TRUE
            """)
    List<Diary> getByShareTypeAndCreatedBetween(@Param("shareType") ShareType shareType,
                                                @Param("dateStart") LocalDateTime dateStart,
                                                @Param("dateEnd") LocalDateTime dateEnd);

    /**
     * 일기 공유 설정, 연령대, 실제 일기 작성한 시각에 따른 조회
     * <p>
     * 공유 허용된 일기만 조회됩니다.
     * <p>
     * ex) 비슷한 연령대 공유를 설정한 20대 사용자가 작성한 일기 중 2023.01.01 21:00:01 ~ 2023.01.02 21:00:00에 작성한 일기 목록
     * @param shareType 공유 설정
     * @param birthStart 사용자 생년 시작
     * @param birthEnd 사용자 생년 끝
     * @param dateStart 조회할 시작 시간
     * @param dateEnd 조회할 끝 시간
     * @return 조건에 해당하는 일기 목록과 작성자 정보
     */
    @Query(value = """
                        SELECT d
                        FROM Diary d
                        JOIN FETCH d.writer wr
                        JOIN FETCH wr.userInfo ui
                        JOIN FETCH wr.settings se
                        WHERE d.createdAt BETWEEN :dateStart AND :dateEnd
                        AND
                        d.shareAgreed = TRUE
                        AND
                        ui.birthYear BETWEEN :birthStart AND :birthEnd
                        AND
                        se.shareType = :shareType
                        AND
                        d.isVisible = TRUE
            """)
    List<Diary> getByShareTypeAndAgeGroupAndCreatedBetween(@Param("shareType") ShareType shareType,
                                                           @Param("birthStart") Integer birthStart,
                                                           @Param("birthEnd") Integer birthEnd,
                                                           @Param("dateStart") LocalDateTime dateStart,
                                                           @Param("dateEnd") LocalDateTime dateEnd);

}
