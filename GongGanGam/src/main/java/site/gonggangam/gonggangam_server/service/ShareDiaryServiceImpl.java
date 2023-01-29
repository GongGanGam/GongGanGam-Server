package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiary;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.DeviceType;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;
import site.gonggangam.gonggangam_server.repository.DiaryRepository;
import site.gonggangam.gonggangam_server.repository.ShareDiaryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Slf4j
public class ShareDiaryServiceImpl implements ShareDiaryService {

    private final DiaryRepository diaryRepository;
    private final ShareDiaryRepository shareDiaryRepository;
    private final ApnsService apnsService;


    /**
     * 매일 21:00:00 일기 공유 작업을 실행합니다.
     * <p>
     * 비슷한 연령대 공유 사용자는 0-9, 10-19, 20-29, ..., 90-99 세까지 나누며, 해당 연령층 그룹별로 공유합니다.
     * <p>
     * 전체 공유 허용 사용자는 동일 설정 사용자끼리 공유합니다.
     */
    @Override
    @Scheduled(cron = "0 0 21 * * ?")
    public void shareAllDiaries() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(1).plusSeconds(1);
        log.info(String.format("[%s] Diary share task start for %s ~ %s", getClass(), start, end));

        for (int ageGroup = 0; ageGroup <= 90; ageGroup += 10) {
            List<Diary> simAgeShare = diaryRepository.getByShareTypeAndAgeGroupAndCreatedBetween(ShareType.SIMILAR, ageGroup, start, end);
            shareGroupedDiaries(simAgeShare);
        }
        List<Diary> allAgeShare = diaryRepository.getByShareTypeAndCreatedBetween(ShareType.ALL, start, end);
        shareGroupedDiaries(allAgeShare);

        log.info(String.format("[%s] Diary share end", getClass()));
    }

    /**
     * 그룹별 셔플하여 공유하되 자신에게 공유되지 않도록 다음과 같은 로직을 이용합니다.
     * <p>
     * 각 일기는 자신의 index + <code>{@link #createOffset(int)}</code> 에서 생성된 offset 위치의 일기와 매칭됩니다.
     * <p>
     * 만약 그룹 크기가 1이하이면 아무것도 실행하지 않습니다.
     * @see #createOffset(int)
     * @param diaries 공유하고자 하는 그룹화된 일기 목록
     */
    private void shareGroupedDiaries(List<Diary> diaries) {
        int offset;
        final int size = diaries.size();

        try {
            offset = createOffset(size);
        } catch (IllegalArgumentException e) {
            return;
        }

        for (int idx = 0; idx < size / 2; idx++) {
            Diary from = diaries.get(idx);
            Diary dest = diaries.get((idx + offset) % size);
            matchingDairies(from, dest);

            notifyDiaryShared(from.getWriter());
            notifyDiaryShared(dest.getWriter());
        }
    }

    /**
     * 1) 0.1 ~ 0.9 사이의 임의의 비율 ratio 를 생성합니다.
     * <p>
     * 2) offset 은 (size) * (ratio) + 1 로 결정됩니다.
     * @param size 일기 그룹의 크기
     * @return size 보다 작게 생성된 offset
     * @throws IllegalStateException 그룹의 크기가 1이하일 때 예외 발생
     */
    private static int createOffset(final int size) throws IllegalArgumentException {
        if (size <= 1) throw new IllegalArgumentException();
        int offset;

        do {
            double ratio = Math.random() * 0.8 + 0.1;
            offset = (int) (size * ratio) + 1;
        } while (offset < size);

        return offset;
    }

    /**
     * 1:1로 매칭된 두 일기를 서로 공유합니다.
     * <p>
     * 트랜잭션으로 실행됩니다.
     * @param diary1 일기 1
     * @param diary2 일기 2
     */
    @Transactional
    public void matchingDairies(Diary diary1, Diary diary2) {
        ShareDiary shareDiary1 = ShareDiary.builder()
                .diary(diary1)
                .receiver(diary2.getWriter())
                .build();

        ShareDiary shareDiary2 = ShareDiary.builder()
                .diary(diary2)
                .receiver(diary1.getWriter())
                .build();

        shareDiaryRepository.save(shareDiary1);
        shareDiaryRepository.save(shareDiary2);
    }

    @Async
    public void notifyDiaryShared(Users receiver) {
        switch (receiver.getDeviceType()) {
            case IOS -> notifyApnsDiaryShared(receiver);
            case ANDROID -> notifyFcmDiaryShared(receiver);
        }
    }

    @Async
    public void notifyApnsDiaryShared(Users receiver) {
        apnsService.notifySharedDiary(receiver.getDeviceToken());
    }

    @Async
    public void notifyFcmDiaryShared(Users receiver) {

    }

}
