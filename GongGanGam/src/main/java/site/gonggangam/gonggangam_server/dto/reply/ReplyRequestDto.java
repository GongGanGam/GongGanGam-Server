package site.gonggangam.gonggangam_server.dto.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class ReplyRequestDto {

    @Data
    @Builder
    @Schema(description = "답장 작성")
    public static class Post {
        @Schema(description = "답장할 일기 ID", defaultValue = "12")
        private final Long diaryId;
        @Schema(description = "답장 내용", defaultValue = "내 손 잡아준 너 매일 아침 눈을 뜰 때면 갓 내린 커피")
        private final String content;
    }

    @Data
    @Builder
    @Schema(description = "답장 수정")
    public static class Put {
        @Schema(description = "답장 내용", defaultValue = "내 손 잡아준 너 매일 아침 눈을 뜰 때면 갓 내린 커피")
        private final String content;
    }
}
