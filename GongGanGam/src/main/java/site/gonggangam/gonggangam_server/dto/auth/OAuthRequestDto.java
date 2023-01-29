package site.gonggangam.gonggangam_server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.users.types.DeviceType;

@Data
@Builder
@Schema(description = "로그인 요청")
public class OAuthRequestDto {
    @Schema(description = "OAuth에서 발급받은 토큰", defaultValue = "oauthtoken")
    private final String token;
    @Schema(description = "디바이스 OS 종류", defaultValue = "ios")
    private final DeviceType deviceType;
    @Schema(description = "사용자 디바이스 토큰", defaultValue = "devicetoken")
    private final String deviceToken;
}
