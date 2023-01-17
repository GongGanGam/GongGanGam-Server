package site.gonggangam.gonggangam_server.dto.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.notice.Notice;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "공지사항 내용")
public class NoticeResponseDto {
    @Schema(description = "공지사항 ID", defaultValue = "14")
    private final Long noticeId;
    @Schema(description = "제목", defaultValue = "공간감 버전 업데이트 안내")
    private final String title;
    @Schema(description = "내용", defaultValue = "안녕하세요. 공간감에서 알려드립니다.")
    private final String content;
    @Schema(description = "생성시간", defaultValue = "2023-01-02 12:23:34")
    private final LocalDateTime createdAt;
    @Schema(description = "최종 수정시간", defaultValue = "2023-01-03 13:11:05")
    private final LocalDateTime updatedAt;

    public static NoticeResponseDto of(Notice entity) {
        return NoticeResponseDto.builder()
                .noticeId(entity.getNoticeId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
