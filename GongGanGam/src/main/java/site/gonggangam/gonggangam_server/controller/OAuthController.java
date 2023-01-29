package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.service.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.service.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.service.dto.auth.OAuthResponseDto;
import site.gonggangam.gonggangam_server.service.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.service.OAuthService;

@Tag(name = "auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @Operation(summary = "Apple 로그인", description = "Apple Oauth 로그인")
    @ResponseBody
    @PostMapping("/login/apple")
    public DataResponseDto<OAuthResponseDto> loginApple(
            @RequestBody OAuthRequestDto body
            ) {
        return null;
    }

    @Operation(summary = "Kakao 로그인", description = "Kakao OAuth 로그인. 카카오로부터 발급받은 토큰 정보를 기반으로 로그인합니다. 만약 해당 이메일의 계정이 없다면 생성됩니다. ")
    @ResponseBody
    @PostMapping("/login/kakao")
    public DataResponseDto<OAuthResponseDto> loginKakao(
            @RequestBody OAuthRequestDto body
    ) {
        return DataResponseDto.of(oAuthService.kakaoLogin(body));
    }

    @Operation(summary = "서비스 회원가입", description = "공간감 회원가입을 위해 사용자 추가정보를 입력합니다. 추가정보를 입력하지 않은 사용자는 이용할 수 없습니다. gender = { unknown, female, male }")
    @ResponseBody
    @PostMapping("/signup")
    public DataResponseDto<String> signup(
            @RequestBody UsersRequestDto.PostUser body
    ) {
        return null;
    }
}
