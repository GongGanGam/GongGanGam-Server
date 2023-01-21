package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiary;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.dto.diary.*;
import site.gonggangam.gonggangam_server.repository.DiaryRepository;
import site.gonggangam.gonggangam_server.repository.ShareDiaryRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private static final Long CALENDAR_SCOPE_WEEKS = 2L;

    private final UsersRepository usersRepository;
    private final DiaryRepository diaryRepository;
    private final ShareDiaryRepository shareDiaryRepository;

    @Override
    @Transactional
    public DiaryResponseDto postDiary(Long userId, DiaryRequestDto.Post request) throws GeneralException {
        Users writer = usersRepository.findById(userId).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND_USER);
        });

        Diary diary = Diary.builder()
                .content(request.getContent())
                .emoji(request.getEmoji())
                .isVisible(true)
                .shareAgreed(request.getShareAgreed())
                .writer(writer)
                .writingDate(request.getDate())
                .build();

        diaryRepository.save(diary);
        return DiaryResponseDto.toDto(diary);
    }

    @Override
    public Page<SharedDiaryResponseDto> getSharedDiaries(Long userId, Pageable pageable) {
        Page<ShareDiary> diaries = shareDiaryRepository.findByReceiverUserId(userId, pageable);

        return new PageImpl<>(
                diaries.stream()
                .map(SharedDiaryResponseDto::toDto)
                .collect(Collectors.toList())
        );
    }

    @Override
    public DiaryResponseDto getDiary(Long diaryId) throws GeneralException {
        Diary diary = diaryRepository.getByDiaryId(diaryId).orElseThrow(() -> {
                    throw new GeneralException(ResponseCode.NOT_FOUND);
                });

        return DiaryResponseDto.toDto(diary);
    }

    @Override
    public CalendarResponseDto getDiaries(Long userId, Integer year, Integer month) {
        LocalDate dest = LocalDate.of(year, month, 1);
        LocalDate start = dest.minusWeeks(CALENDAR_SCOPE_WEEKS);
        LocalDate end = dest.plusMonths(1).plusWeeks(CALENDAR_SCOPE_WEEKS);

        List<Diary> diaries = diaryRepository.getByUserIdAndBetweenDate(userId, start, end);

        return CalendarResponseDto.builder()
                .prevMonth(getCalendarResponseByMonth(diaries, start.getMonthValue()))
                .destMonth(getCalendarResponseByMonth(diaries, dest.getMonthValue()))
                .nextMonth(getCalendarResponseByMonth(diaries, end.getMonthValue()))
                .build();
    }

    private static List<DiaryPreviewResponseDto> getCalendarResponseByMonth(List<Diary> diaries, int month) {
        return diaries
                .stream()
                .filter(diary -> diary.getWritingDate().getMonthValue() == month)
                .map(DiaryPreviewResponseDto::toDto)
                .toList();
    }

    @Override
    public DiaryResponseDto putDiary(Long diaryId, DiaryRequestDto.Put request) throws GeneralException{
        Diary diary = diaryRepository.getByDiaryId(diaryId).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND);
        });

        diary.update(request.getEmoji(), request.getContent(), request.getShareAgreed());

        diaryRepository.save(diary);
        return DiaryResponseDto.toDto(diary);
    }

    @Override
    public void deleteDiary(Long diaryId) throws GeneralException {
        Diary diary = diaryRepository.getByDiaryId(diaryId).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND);
        });

        diary.delete();
        diaryRepository.save(diary);
    }
}
