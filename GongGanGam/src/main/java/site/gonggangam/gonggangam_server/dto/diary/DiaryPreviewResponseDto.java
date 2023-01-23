package site.gonggangam.gonggangam_server.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.diary.Diary;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "캘린더에서 조회되는 한 날짜의 일기 정보")
public class DiaryPreviewResponseDto {
    @Schema(description = "일기 ID", defaultValue = "12")
    private final Long diaryId;
    @Schema(description = "날짜", defaultValue = "2023-01-04")
    private final LocalDate date;
    @Schema(description = "이모지", defaultValue = "happy")
    private final String emoji;

    public static DiaryPreviewResponseDto of(Diary entity) {
        return DiaryPreviewResponseDto.builder()
                .diaryId(entity.getDiaryId())
                .date(entity.getWritingDate())
                .emoji(entity.getEmoji())
                .build();
    }
}
