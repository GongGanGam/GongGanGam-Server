package site.gonggangam.gonggangam_server.service.dto.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.service.dto.users.WriterDto;

import java.time.LocalDateTime;

@Builder
public record ReplyContentDto(
        @Schema(description = "답장 ID", defaultValue = "41")
        Long replyId,
        @Schema(description = "답장 내용", defaultValue = "내 손 잡아준 너 매일 아침 눈을 뜰 때면 갓 내린 커피")
        String content,
        @Schema(description = "작성자")
        WriterDto writer,
        @Schema(description = "답장 생성시간", defaultValue = "2023-02-06T09:10:35.145Z")
        LocalDateTime createdAt,
        @Schema(description = "답장 최종수정 시간", defaultValue = "2023-02-06T09:10:35.145Z")
        LocalDateTime updatedAt
) {
        public static ReplyContentDto of(Reply entity) {
                return ReplyContentDto.builder()
                        .replyId(entity.getReplyId())
                        .content(entity.getContent())
                        .writer(WriterDto.of(entity.getWriter()))
                        .createdAt(entity.getCreatedAt())
                        .updatedAt(entity.getUpdatedAt())
                        .build();
        }
}
