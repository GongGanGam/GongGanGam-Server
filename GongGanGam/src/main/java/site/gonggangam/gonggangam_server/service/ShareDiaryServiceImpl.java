package site.gonggangam.gonggangam_server.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiary;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;
import site.gonggangam.gonggangam_server.domain.repository.DiaryRepository;
import site.gonggangam.gonggangam_server.domain.repository.ShareDiaryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
     * 비슷한 연령대 공유 사용자는 10-19, 20-29, ..., 90-99 세까지 나누며, 해당 연령층 그룹별로 공유합니다.
     * <p>
     * 전체 공유 허용 사용자는 동일 설정 사용자끼리 공유합니다.
     */
    @Override
    @Scheduled(cron = "0 15 0 * * ?")
    public void shareAllDiaries() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(1).plusSeconds(1);
        log.info(String.format("Diary share task start for %s ~ %s", start, now));

        for (int ageGroup = 10; ageGroup <= 90; ageGroup += 10) {
            LocalDate birthEnd = LocalDate.now().minusYears(ageGroup - 1);
            LocalDate birthStart = birthEnd.minusYears(9);

            List<Diary> simAgeShare = diaryRepository.getByShareTypeAndAgeGroupAndCreatedBetween(
                    ShareType.SIMILAR,
                    birthStart.getYear(),
                    birthEnd.getYear(),
                    start,
                    now);
            shareGroupedDiaries(simAgeShare);
        }
        List<Diary> allAgeShare = diaryRepository.getByShareTypeAndCreatedBetween(ShareType.ALL, start, now);
        shareGroupedDiaries(allAgeShare);

        log.info(String.format("Diary share end at %s", LocalDateTime.now()));
    }

    /**
     * 그룹화된 일기들을 공유합니다. <p>
     * 각 일기에 대해서 그룹 내 다른 일기 작성자에게 공유합니다. <p>
     * 공유 수신자는 항상 자기 자신이 아닙니다. <p>
     * 다음의 로직을 사용합니다. <p>
     * 1) {@link #createOffset(int)}을 이용해 {@code offset}을 생성합니다. <p>
     * 2) 1개의 일기를 작성한 작성자의 일기는 일기의 ({@code index} + {@code offset}) % {@code size} 위치의 일기의 작성자에게 공유됩니다. <p>
     * 3) 1개를 초과한 작성자의 나머지 일기는 {@link #createDestIndex(Set, int)} 를 이용해 자신의 일기를 블랙리스트로 추가하여 다른 일기와 매칭됩니다.
     * @see #createOffset(int)
     * @see #createDestIndex(Set, int)
     * @param diaries 그룹화된 일기 목록
     */
    private void shareGroupedDiaries(List<Diary> diaries) {
        Set<Users> diarySharedUsers = new HashSet<>();
        List<PackedDiaries> packedByWriter = packByWriterId(diaries);

        final int size = packedByWriter.size();
        if (size <= 1) return;

        int offset = createOffset(size);

        for (int idx = 0; idx < size; idx++) {
            sharePackedDiaries(size, offset, idx, packedByWriter, diarySharedUsers, diaries);
        }
    }

    /**
     * 한 작성자가 쓴 일기들을 공유합니다.
     * @param size 공유그룹 내 작성자 수
     * @param offset {@link #createOffset(int)} 으로부터 생성된 offset
     * @param idx {@code PackedDiaries}의 위치
     * @param packedByWriters 작성자로 패킹된 일기 목록
     * @param sharedUsers 이미 공유한 사용자
     * @param diaries 공유그룹 내 전체 일기 목록
     */
    private void sharePackedDiaries(final int size,
                                    final int offset,
                                    final int idx,
                                    final List<PackedDiaries> packedByWriters,
                                    final Set<Users> sharedUsers,
                                    final List<Diary> diaries) {

        PackedDiaries packedDiaries = packedByWriters.get(idx);

        for (DiaryWithIdx diaryInfo : packedDiaries.diaries) {
            Users receiver;

            // 아직 한번도 공유를 못한 사용자는 offset 으로 처리
            if (!sharedUsers.contains(packedDiaries.writer)) {
                receiver = packedByWriters.get((idx + offset) % size).getWriter();
            }
            // 한번 공유를 한 사용자는 전체 일기 목록 중에서 랜덤 index 생성으로 처리
            else {
                Set<Integer> blacklist = packedDiaries.getDiaries()
                        .stream()
                        .map(DiaryWithIdx::index)
                        .collect(Collectors.toSet());
                int destDiaryIdx = createDestIndex(blacklist, size);
                receiver = diaries.get(destDiaryIdx).getWriter();
            }

            // 일기 공유
            shareDiary(diaryInfo.diary, receiver);
            sharedUsers.add(packedDiaries.writer);

            // 알림 설정 되어 있을 경우에만 푸시알림
            if (receiver.getSettings().getNotifyDiary()) {
                notifyDiaryShared(receiver);
            }
        }

    }

    /**
     * 작성자 id로 일기를 묶어서 반환합니다.
     * @param diaries 일기 목록
     * @return 작성자로 구분된 일기목록
     */
    private List<PackedDiaries> packByWriterId(List<Diary> diaries) {
        List<PackedDiaries> packedDiaries = new ArrayList<>();
        MultiValueMap<Users, DiaryWithIdx> packedByWriter = new LinkedMultiValueMap<>();

        for (int idx = 0; idx < diaries.size(); idx++) {
            Diary diary = diaries.get(idx);
            packedByWriter.add(diary.getWriter(), new DiaryWithIdx(diary, idx));
        }

        packedByWriter.forEach((writer, diaryWithIdxes) -> {
            packedDiaries.add(new PackedDiaries(writer, diaryWithIdxes));
        });

        return packedDiaries;
    }

    /**
     * 1) 0.1 ~ 0.9 사이의 임의의 비율 ratio 를 생성합니다.
     * <p>
     * 2) offset 은 (size) * (ratio) + 1 로 결정됩니다.
     * @param size 일기 공유그룹 내 작성자의 수
     * @return size 보다 작게 생성된 offset
     * @throws IllegalStateException 그룹의 크기가 1이하일 때 예외 발생
     */
    private int createOffset(final int size) throws IllegalArgumentException {
        if (size <= 1) throw new IllegalArgumentException(String.format("[%s] Diary group size must larger than 1.", getClass()));
        int offset;

        do {
            double ratio = Math.random() * 0.8 + 0.1;
            offset = (int) (size * ratio) + 1;
        } while (offset >= size);

        return offset;
    }


    /**
     * 공유할 일기 위치를 생성합니다.
     * <p>
     * 반환 값은 0 이상 {@code size} 미만 범위의 정수 중 현재 일기의 위치인 {@code curIdx} 와 다른 것입니다.
     * @param blacklist 뽑지 않을 일기의 위치 (동일 일기 선택 방지)
     * @param size 일기 그룹의 크기
     * @return 0 이상 {@code size} 미만의 정수 중 {@code curIdx} 가 아닌 임의의 정수
     * @throws IllegalStateException 그룹의 크기가 1이하일 때 예외 발생
     */
    private int createDestIndex(final Set<Integer> blacklist, final int size) throws IllegalArgumentException {
        if (size <= 1) throw new IllegalArgumentException(String.format("[%s] Diary group size must larger than 1.", getClass()));

        Random random = new Random(System.currentTimeMillis());
        int index;

        do {
            index = random.nextInt(size);
        } while (blacklist.contains(index));

        return index;
    }

    /**
     * 일기를 공유합니다.
     * @param diary 공유할 일기
     * @param receiver 공유받을 사용자
     */
    public void shareDiary(Diary diary, Users receiver) {
        ShareDiary shareDiary = ShareDiary.builder()
                .diary(diary)
                .receiver(receiver)
                .build();

        shareDiaryRepository.save(shareDiary);
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

    @Data
    private final static class PackedDiaries {
        private final Users writer;
        private final List<DiaryWithIdx> diaries;
    }
    private record DiaryWithIdx(Diary diary, Integer index) {}
}
