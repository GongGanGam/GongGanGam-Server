package site.gonggangam.gonggangam_server.controller.dto.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.controller.dto.users.WriterDto;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "받은 답장 목록에서 보여질 정보")
public class ReplyPreviewResponseDto {
    @Schema(description = "답장 ID", defaultValue = "41")
    private final Long replyId;
    @Schema(description = "작성자")
    private final WriterDto writer;
    @Schema(description = "답장 내용", defaultValue = "기억하니 우리가 했던 이별")
    private final String content;
    @Schema(description = "답장 생성시간", defaultValue = "2023-01-05")
    private final LocalDateTime createdAt;
    @Schema(description = "답장 최종수정 시간", defaultValue = "2023-01-06")
    private final LocalDateTime updatedAt;

    public static ReplyPreviewResponseDto of(Reply entity) {
        return ReplyPreviewResponseDto.builder()
                .replyId(entity.getReplyId())
                .writer(WriterDto.of(entity.getWriter()))
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
