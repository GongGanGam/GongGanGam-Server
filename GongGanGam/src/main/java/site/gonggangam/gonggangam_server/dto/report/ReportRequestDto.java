package site.gonggangam.gonggangam_server.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public interface ReportRequestDto {

    @Data
    @Builder
    @Schema(description = "신고 등록")
    class PostReport {
        @Schema(description = "신고 대상 ID", defaultValue = "12")
        private final Long targetId;
        @Schema(description = "타입", defaultValue = "diary", allowableValues = {"diary", "reply", "chat"})
        private final ReportType type;
        @Schema(description = "신고 사유", defaultValue = "욕설, 비방")
        private final String reason;
    }

    @Data
    @Builder
    @Schema(description = "신고 상태 변경")
    class PutReport {
        @Schema(description = "신고내역 ID", defaultValue = "31")
        private final Long reportId;
        @Schema(description = "신고 상태", allowableValues = {"before", "processing", "completed"}, defaultValue = "completed")
        private final String progress;
    }
}
