package site.gonggangam.gonggangam_server.service.dto.diary;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "연도, 월로 조회한 일기 목록")
public class CalendarResponseDto {
    private final List<DiaryResponseDto> prevMonth;
    private final List<DiaryResponseDto> destMonth;
    private final List<DiaryResponseDto> nextMonth;
}
