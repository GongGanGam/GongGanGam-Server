package site.gonggangam.gonggangam_server.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class ReportRequestDto {

    @Data
    @Builder
    @Schema(description = "신고 등록")
    public static class PostReport {
        @Schema(description = "신고 대상 ID", defaultValue = "12")
        private final Long targetId;
        @Schema(description = "타입", defaultValue = "diary", allowableValues = {"diary", "reply", "chat"})
        private final String type;
        @Schema(description = "신고 사유", defaultValue = "욕설, 비방")
        private final String reason;
    }

    @Data
    @Builder
    @Schema(description = "신고 상태 변경")
    public static class PutReport {
        @Schema(description = "신고 상태", allowableValues = {"before", "processing", "completed"}, defaultValue = "completed")
        private final String progress;
    }
}