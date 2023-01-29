package site.gonggangam.gonggangam_server.controller.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 설정 수정")
public class UserSettingsRequestDto {
    @Schema(description = "받은 일기 알림", defaultValue = "true")
    private final Boolean notifyDiary;
    @Schema(description = "받은 답장 알림", defaultValue = "true")
    private final Boolean notifyReply;
    @Schema(description = "채팅 알림", defaultValue = "true")
    private final Boolean notifyChat;
}
