package site.gonggangam.gonggangam_server.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.gonggangam.gonggangam_server.domain.report.ReportReply;
import site.gonggangam.gonggangam_server.dto.users.WriterDto;
import site.gonggangam.gonggangam_server.dto.reply.ReplyResponseDto;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "신고된 답장")
public class ReplyReportResponseDto extends ReportResponseDto {

    @Schema(description = "신고된 답장 정보")
    private final ReplyResponseDto reply;

    @Builder
    public ReplyReportResponseDto(Long reportId, String reason, String progress, WriterDto reporter, LocalDateTime createdAt, LocalDateTime updatedAt, ReplyResponseDto reply) {
        super(reportId, reason, progress, reporter, createdAt, updatedAt);
        this.reply = reply;
    }

    public static ReplyReportResponseDto of(ReportReply entity) {
        return ReplyReportResponseDto.builder()
                .reportId(entity.getReportId())
                .reason(entity.getReason())
                .progress(entity.getProcessType().getTitle())
                .reporter(WriterDto.of(entity.getReporter()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .reply(ReplyResponseDto.of(entity.getReply()))
                .build();
    }
}
