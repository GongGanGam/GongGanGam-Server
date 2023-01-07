package site.gonggangam.gonggangam_server.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.gonggangam.gonggangam_server.dto.diary.SharedDiaryResponseDto;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Schema(description = "신고된 일기")
public class DiaryReportResponseDto extends ReportResponseDto {
    @Schema(description = "신고된 일기 정보")
    private final SharedDiaryResponseDto diary;
}
