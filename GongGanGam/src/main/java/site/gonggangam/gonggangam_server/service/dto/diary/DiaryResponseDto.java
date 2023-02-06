package site.gonggangam.gonggangam_server.service.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.service.dto.reply.ReplyContentDto;
import site.gonggangam.gonggangam_server.service.dto.reply.ReplyResponseDto;

import java.time.LocalDate;

@Builder
@Schema(description = "일기 상세조회")
public record DiaryResponseDto(
        DiaryContentDto diary,
        Boolean hasReply,
        ReplyContentDto reply
) {
    public static DiaryResponseDto of(Diary diary) {
        return DiaryResponseDto.builder()
                .diary(DiaryContentDto.of(diary))
                .hasReply(false)
                .reply(null)
                .build();
    }

    public static DiaryResponseDto of(Diary diary, Reply reply) {
        return DiaryResponseDto.builder()
                .diary(DiaryContentDto.of(diary))
                .hasReply(true)
                .reply(ReplyContentDto.of(reply))
                .build();
    }
}
