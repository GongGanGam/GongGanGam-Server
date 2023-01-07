package site.gonggangam.gonggangam_server.dto.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class NoticeRequestDto {

    @Data
    @Builder
    @Schema(description = "공지사항 생성")
    public static class PostNotice {
        @Schema(description = "제목", defaultValue = "공간감 버전 업데이트 안내")
        private final String title;
        @Schema(description = "내용", defaultValue = "안녕하세요. 공간감에서 알려드립니다.")
        private final String content;
    }

    @Data
    @Builder
    @Schema(description = "공지사항 수정")
    public static class PutNotice {
        @Schema(description = "타이틀", defaultValue = "공간감 버전 업데이트 안내")
        private final String title;
        @Schema(description = "내용", defaultValue = "안녕하세요. 공간감에서 알려드립니다.")
        private final String content;
    }
}
