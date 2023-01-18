package site.gonggangam.gonggangam_server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema()
public class OAuthRequestDto {
    @Schema(description = "OAuth에서 발급받은 토큰", defaultValue = "oauthtoken")
    private final String token;
    @Schema(description = "사용자 디바이스 토큰", defaultValue = "devicetoken")
    private final String deviceToken;
}
