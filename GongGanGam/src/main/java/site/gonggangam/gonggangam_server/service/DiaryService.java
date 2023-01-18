package site.gonggangam.gonggangam_server.service;

import org.springframework.data.domain.Pageable;
import site.gonggangam.gonggangam_server.dto.diary.CalendarResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryRequestDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.SharedDiaryResponseDto;

import java.util.List;

public interface DiaryService {

    DiaryResponseDto postDiary(Long userId, DiaryRequestDto.Post request);

    List<SharedDiaryResponseDto> getSharedDiaries(Long userId, Pageable pageable);

    DiaryResponseDto getDiary(Long diaryId);

    CalendarResponseDto getDiaries(Long userId, Integer year, Integer month);

    DiaryResponseDto putDiary(DiaryRequestDto.Put request);

    void deleteDiary(Long diaryId);
}
