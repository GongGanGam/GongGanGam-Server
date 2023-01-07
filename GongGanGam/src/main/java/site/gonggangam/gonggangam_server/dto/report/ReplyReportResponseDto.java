package site.gonggangam.gonggangam_server.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.gonggangam.gonggangam_server.dto.reply.ReplyResponseDto;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(description = "신고된 답장")
public class ReplyReportResponseDto extends ReportResponseDto {
    @Schema(description = "신고된 답장 정보")
    private final ReplyResponseDto reply;
}
