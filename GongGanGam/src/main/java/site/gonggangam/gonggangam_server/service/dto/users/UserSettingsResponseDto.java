package site.gonggangam.gonggangam_server.service.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 설정 정보")
public class UserSettingsResponseDto {

    @Schema(description = "받은 일기 알림", defaultValue = "true")
    private final Boolean notifyDiary;
    @Schema(description = "받은 답장 알림", defaultValue = "true")
    private final Boolean notifyReply;
    @Schema(description = "채팅 알림", defaultValue = "true")
    private final Boolean notifyChat;

}
