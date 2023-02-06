package site.gonggangam.gonggangam_server.service.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import site.gonggangam.gonggangam_server.domain.diary.Diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Schema(description = "일기 내용")
public record DiaryContentDto(
        @Schema(description = "일기 ID", defaultValue = "12")
        Long diaryId,
        @Schema(description = "날짜", defaultValue = "2023-01-04")
        LocalDate date,
        @Schema(description = "이모지", defaultValue = "happy")
        String emoji,
        @Schema(description = "내용", defaultValue = "작게 열어둔 문틈 사이로")
        String content,
        @Schema(description = "업로드된 이미지", defaultValue = "https://imgurl.com")
        String imgUrl,
        @Schema(description = "공유 허용 여부", defaultValue = "true")
        Boolean shareAgreed,
        @Schema(description = "일기 생성시간", defaultValue = "2023-02-06T09:10:35.145Z")
        LocalDateTime createdAt,
        @Schema(description = "일기 최종수정 시간", defaultValue = "2023-02-06T09:10:35.145Z")
        LocalDateTime updatedAt

) {
        public static DiaryContentDto of(Diary entity) {
                return DiaryContentDto.builder()
                        .diaryId(entity.getDiaryId())
                        .date(entity.getDiaryDate())
                        .emoji(entity.getEmoji())
                        .content(entity.getContent())
                        .imgUrl(entity.getImgUrl())
                        .shareAgreed(entity.getShareAgreed())
                        .build();
        }
}
