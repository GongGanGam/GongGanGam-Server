package site.gonggangam.gonggangam_server.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.gonggangam.gonggangam_server.domain.report.ReportDiary;
import site.gonggangam.gonggangam_server.dto.diary.SharedDiaryResponseDto;
import site.gonggangam.gonggangam_server.dto.users.WriterDto;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "신고된 일기")
public class DiaryReportResponseDto extends ReportResponseDto {

    @Schema(description = "신고된 일기 정보")
    private final SharedDiaryResponseDto diary;

    @Builder
    public DiaryReportResponseDto(Long reportId, String reason, String progress, WriterDto reporter, LocalDateTime createdAt, LocalDateTime updatedAt, SharedDiaryResponseDto diary) {
        super(reportId, reason, progress, reporter, createdAt, updatedAt);
        this.diary = diary;
    }

    public static DiaryReportResponseDto toDto(ReportDiary entity) {
        return DiaryReportResponseDto.builder()
                .reportId(entity.getReportId())
                .reason(entity.getReason())
                .progress(entity.getProcessType().getTitle())
                .reporter(WriterDto.toDto(entity.getReporter()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .diary(SharedDiaryResponseDto.toDto(entity.getDiary()))
                .build();
    }
}
