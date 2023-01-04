package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.dto.auth.OAuthResponseDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;

@Tag(name = "auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "Apple 로그인", description = "Apple Oauth 로그인")
    @ResponseBody
    @PostMapping("/login/apple")
    public ResponseEntity<OAuthResponseDto> loginApple(
            @RequestBody OAuthRequestDto body
            ) {
        return null;
    }

    @Operation(summary = "Kakao 로그인", description = "Kakao OAuth 로그인")
    @ResponseBody
    @PostMapping("/login/kakao")
    public ResponseEntity<OAuthResponseDto> loginKakao(
            @RequestBody OAuthRequestDto body
    ) {
        return null;
    }

    @Operation(summary = "회원가입", description = "회원가입을 위해 사용자 추가정보를 입력합니다.")
    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody UsersRequestDto.Post body
    ) {
        return null;
    }
}
