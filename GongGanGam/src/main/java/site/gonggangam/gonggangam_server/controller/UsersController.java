package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsResponseDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersResponseDto;

@Tag(name = "user", description = "사용자 관련 API")
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsersController {

    @Operation(summary = "내 정보 조회", description = "마이페이지에서 사용자의 정보를 조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))),
                    @ApiResponse(responseCode = "401", description ="인증에 실패하였습니다", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "403", description ="만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "404", description ="잘못된 접근입니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))
            }
    )
    @ResponseBody
    @GetMapping("/info")
    public ResponseEntity<UsersResponseDto> getUserInfo(
    ) {
        return null;
    }

    @Operation(summary = "사용자 설정 조회", description = "마이페이지에서 사용자의 알림 설정을 조회힙니다.")
    @ResponseBody
    @GetMapping("/settings")
    public ResponseEntity<UserSettingsResponseDto> getUserSettings(
    ) {
        return null;
    }

    @Operation(summary = "사용자 설정 변경", description = "마이페이지에서 사용자 알림 관련 설정을 변경합니다.")
    @ResponseBody
    @PutMapping("/settings")
    public ResponseEntity<String> putUserSettings(
            @RequestBody UserSettingsRequestDto body
            ) {
        return null;
    }

    @Operation(summary = "회원정보 수정", description = "마이페이지에서 회원정보를 수정합니다.")
    @ResponseBody
    @PutMapping("/info")
    public ResponseEntity<String> putUserInfo(
            @RequestBody UsersRequestDto.PutUserInfo body
    ) {
        return null;
    }

    @Operation(summary = "프로필 이미지 수정", description = "마이페이지에서 사용자 프로필 이미지를 업로드합니다.")
    @ResponseBody
    @PutMapping(value = "/profImg",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putUserProfImg(
            @Parameter(description = "프로필 이미지를 multipart/form-data 형식으로 받습니다.")
            @RequestPart(value = "imgFile", required = true) MultipartFile imgFile
            ) {
        return null;
    }

    @Operation(summary = "회원 탈퇴", description = "사용자를 회원 탈퇴 처리합니다.")
    @ResponseBody
    @DeleteMapping
    public ResponseEntity<String> deleteUser(
    ) {
        return null;
    }

}
