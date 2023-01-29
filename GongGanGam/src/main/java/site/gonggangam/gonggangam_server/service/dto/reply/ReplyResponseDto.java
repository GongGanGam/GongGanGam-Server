package site.gonggangam.gonggangam_server.service.dto.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.service.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.service.dto.users.WriterDto;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "일기 답장")
public class ReplyResponseDto {
    @Schema(description = "기존 일기")
    private final DiaryResponseDto diary;
    @Schema(description = "답장 ID", defaultValue = "41")
    private final Long replyId;
    @Schema(description = "답장 내용", defaultValue = "내 손 잡아준 너 매일 아침 눈을 뜰 때면 갓 내린 커피")
    private final String content;
    @Schema(description = "작성자")
    private final WriterDto writer;
    @Schema(description = "답장 생성시간", defaultValue = "2023-01-05")
    private final LocalDateTime createdAt;
    @Schema(description = "답장 최종수정 시간", defaultValue = "2023-01-06")
    private final LocalDateTime updatedAt;

    public static ReplyResponseDto of(Reply entity) {
        return ReplyResponseDto.builder()
                .diary(DiaryResponseDto.of(entity.getDiary()))
                .replyId(entity.getReplyId())
                .content(entity.getContent())
                .writer(WriterDto.of(entity.getWriter()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
