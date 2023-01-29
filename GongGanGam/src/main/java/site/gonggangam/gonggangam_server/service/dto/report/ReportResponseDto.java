package site.gonggangam.gonggangam_server.service.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import site.gonggangam.gonggangam_server.service.dto.users.WriterDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "신고 내역")
public abstract class ReportResponseDto {
    @Schema(description = "신고내역 ID", defaultValue = "14")
    private Long reportId;
    @Schema(description = "신고 사유", defaultValue = "욕설, 비방")
    private String reason;
    @Schema(description = "처리진행 상황", allowableValues = {"before", "processing", "completed"}, defaultValue = "before")
    private String progress;
    @Schema(description = "신고자")
    private WriterDto reporter;
    @Schema(description = "신고 생성시간", defaultValue = "2023-01-05 11:11:11")
    private LocalDateTime createdAt;
    @Schema(description = "신고 수정시간", defaultValue = "2023-01-06 12:23:51")
    private LocalDateTime updatedAt;
}
