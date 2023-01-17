package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
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
    public List<SharedDiaryResponseDto> getSharedDiaries(Long userId, Integer page, Integer pageSize) {
        // TODO : paging 구현
        List<ShareDiary> diaries = shareDiaryRepository.findAllByReceiver_UserIdAndDiary_IsVisible(userId, true);

        return diaries.stream()
                .map(SharedDiaryResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiaryResponseDto getDiary(Long diaryId) throws GeneralException {
        Diary diary = diaryRepository.findByDiaryIdAndIsVisible(diaryId, true).orElseThrow(() -> {
                    throw new GeneralException(ResponseCode.NOT_FOUND);
                });

        return DiaryResponseDto.toDto(diary);
    }

    @Override
    public CalendarResponseDto getDiaries(Long userId, Integer year, Integer month) {
        // TODO 수정
        LocalDate after = LocalDate.of(year, month, 1);
        LocalDate before = LocalDate.of(year, month + 1, 1);

        List<Diary> diaries = diaryRepository.findAllByWriter_UserIdAndWritingDateIsAfterAndWritingDateIsBeforeAndIsVisible(userId, after, before, true);
        List<DiaryPreviewResponseDto> destMonth = diaries.stream()
                .map(DiaryPreviewResponseDto::toDto).toList();

        return CalendarResponseDto.builder()
                .destMonth(destMonth)
                .build();
    }

    @Override
    public DiaryResponseDto putDiary(DiaryRequestDto.Put request) throws GeneralException{
        Diary diary = diaryRepository.findByDiaryIdAndIsVisible(request.getDiaryId(), true).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND);
        });

        // TODO : shared agree 변경시 로직 추가
        diary.update(request.getEmoji(), request.getContent(), request.getShareAgreed());

        diaryRepository.save(diary);
        return DiaryResponseDto.toDto(diary);
    }

    @Override
    public void deleteDiary(Long diaryId) throws GeneralException {
        Diary diary = diaryRepository.findByDiaryIdAndIsVisible(diaryId, true).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND);
        });

        diary.delete();
        diaryRepository.save(diary);
    }
}
