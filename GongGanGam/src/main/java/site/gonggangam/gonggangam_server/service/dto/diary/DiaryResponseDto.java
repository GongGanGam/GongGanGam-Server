package site.gonggangam.gonggangam_server.service.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.diary.Diary;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "일기 상세조회")
public class DiaryResponseDto {
    @Schema(description = "일기 ID", defaultValue = "12")
    private final Long diaryId;
    @Schema(description = "날짜", defaultValue = "2023-01-04")
    private final LocalDate date;
    @Schema(description = "이모지", defaultValue = "happy")
    private final String emoji;
    @Schema(description = "내용", defaultValue = "작게 열어둔 문틈 사이로")
    private final String content;
    @Schema(description = "업로드된 이미지", defaultValue = "https://imgurl.com")
    private final String imgUrl;
    @Schema(description = "공유 허용 여부", defaultValue = "true")
    private final Boolean shareAgreed;

    public static DiaryResponseDto of(Diary entity) {
        return DiaryResponseDto.builder()
                .diaryId(entity.getDiaryId())
                .date(entity.getDiaryDate())
                .emoji(entity.getEmoji())
                .content(entity.getContent())
                .imgUrl(entity.getImgUrl())
                .shareAgreed(entity.getShareAgreed())
                .build();
    }
}
