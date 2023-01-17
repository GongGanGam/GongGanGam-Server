package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.dto.diary.CalendarResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryRequestDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.SharedDiaryResponseDto;

import java.util.List;

public interface DiaryService {

    DiaryResponseDto postDiary(Long userId, DiaryRequestDto.Post request);

    List<SharedDiaryResponseDto> getSharedDiaries(Long userId, Integer page, Integer pageSize);

    DiaryResponseDto getDiary(Long diaryId);

    CalendarResponseDto getDiaries(Long userId, Integer year, Integer month);

    DiaryResponseDto putDiary(DiaryRequestDto.Put request);

    void deleteDiary(Long diaryId);
}
