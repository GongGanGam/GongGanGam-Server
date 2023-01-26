package site.gonggangam.gonggangam_server.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.gonggangam.gonggangam_server.dto.diary.CalendarResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryRequestDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.SharedDiaryResponseDto;

public interface DiaryService {

    DiaryResponseDto postDiary(Long userId, DiaryRequestDto.PostDiary request);

    Page<SharedDiaryResponseDto> getSharedDiaries(Long userId, Pageable pageable);

    DiaryResponseDto getDiary(Long diaryId);

    CalendarResponseDto getDiaries(Long userId, Integer year, Integer month);

    DiaryResponseDto putDiary(Long diaryId, DiaryRequestDto.PutDiary request);

    void deleteDiary(Long diaryId);
}
