package site.gonggangam.gonggangam_server.service.dto.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.controller.DiaryController;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.service.dto.diary.DiaryContentDto;
import site.gonggangam.gonggangam_server.service.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.service.dto.users.WriterDto;

import java.time.LocalDateTime;

@Builder
@Schema(description = "일기 답장")
public record ReplyResponseDto(
        @Schema(description = "기존 일기")
        DiaryContentDto diary,
        @Schema(description = "답장 정보")
        ReplyContentDto reply
)
{
    public static ReplyResponseDto of(Reply entity) {
        return ReplyResponseDto.builder()
                .diary(DiaryContentDto.of(entity.getDiary()))
                .reply(ReplyContentDto.of(entity))
                .build();
    }
}
