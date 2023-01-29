package site.gonggangam.gonggangam_server.controller.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "OAuth 로그인 결과")
public class OAuthResponseDto {
    @Schema(description = "인증에 사용될 토큰. 유효기간 = 1시간", defaultValue = "aaa.bbb.ccc")
    private final String accessToken;
    @Schema(description = "토큰 갱신에 사용할 리프레시 토큰. 유효기간 = 1달", defaultValue = "aaa.bbb.ccc")
    private final String refreshToken;
}
