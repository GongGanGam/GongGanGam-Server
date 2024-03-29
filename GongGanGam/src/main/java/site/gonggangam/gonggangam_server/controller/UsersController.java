package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodeGroup;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodes;
import site.gonggangam.gonggangam_server.service.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ResponseDto;
import site.gonggangam.gonggangam_server.service.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.service.dto.users.UserSettingsResponseDto;
import site.gonggangam.gonggangam_server.service.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.service.dto.users.UsersResponseDto;

@Tag(name = "user", description = "사용자 관련 API")
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsersController {

    @Operation(summary = "내 정보 조회", description = "마이페이지에서 사용자의 정보를 조회합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @GetMapping("/info")
    public DataResponseDto<UsersResponseDto> getUserInfo(
    ) {
        return null;
    }

    @Operation(summary = "사용자 설정 조회", description = "마이페이지에서 사용자의 알림 설정을 조회힙니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @GetMapping("/settings")
    public DataResponseDto<UserSettingsResponseDto> getUserSettings(
    ) {
        return null;
    }

    @Operation(summary = "사용자 설정 변경", description = "마이페이지에서 사용자 알림 관련 설정을 변경합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK),
                    @ApiResponseCode(ResponseCode.BAD_REQUEST)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @PutMapping("/settings")
    public DataResponseDto<UsersResponseDto> putUserSettings(
            @RequestBody UserSettingsRequestDto body
            ) {
        return null;
    }

    @Operation(summary = "회원정보 수정", description = "마이페이지에서 회원정보를 수정합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK),
                    @ApiResponseCode(ResponseCode.BAD_REQUEST)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @PutMapping("/info")
    public DataResponseDto<UserSettingsResponseDto> putUserInfo(
            @RequestBody UsersRequestDto.PutUserInfo body
    ) {
        return null;
    }

    @Operation(summary = "프로필 이미지 수정", description = "마이페이지에서 사용자 프로필 이미지를 업로드합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.CREATED),
                    @ApiResponseCode(ResponseCode.BAD_REQUEST)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @PutMapping(
            value = "/profImg",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseDto putUserProfImg(
            @Parameter(description = "프로필 이미지를 multipart/form-data 형식으로 받습니다.")
            @RequestPart(value = "imgFile", required = true) MultipartFile imgFile
    ) {
        return null;
    }

    @Operation(summary = "회원 탈퇴", description = "사용자를 회원 탈퇴 처리합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @DeleteMapping
    public ResponseDto deleteUser(
    ) {
        return null;
    }

}
