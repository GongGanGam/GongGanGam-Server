package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.dto.ResponseDto;
import site.gonggangam.gonggangam_server.dto.SuccessResponseDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeRequestDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeResponseDto;
import site.gonggangam.gonggangam_server.service.NoticeService;

import java.util.List;

@Tag(name = "notice", description = "공지사항 관련 API")
@RestController
@RequestMapping(value = "/api/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor

// TODO : userId 구현
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 작성", description = "관리자 계정만 이용 가능합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "작성 성공"),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping
    public DataResponseDto<NoticeResponseDto> postNotice(
            @RequestBody NoticeRequestDto.PostNotice body
            ) {
        return DataResponseDto.of(noticeService.postNotice(1L, body));
    }

    @Operation(summary = "공지사항 수정", description = "관리자 계정만 이용 가능합니다.")
    @PutMapping("/{noticeId}")
    public DataResponseDto<NoticeResponseDto> putNotice(
            @PathVariable("noticeId") Long noticeId,
            @RequestBody NoticeRequestDto.PutNotice body
    ) {
        return DataResponseDto.of(noticeService.putNotice(noticeId, body));
    }

    @Operation(summary = "공지사항 조회", description = "공지사항 목록을 조회합니다.")
    @GetMapping
    public DataResponseDto<List<NoticeResponseDto>> getNoticeList(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
    ) {
        return DataResponseDto.of(noticeService.getNoticeList(page, pageSize));
    }

}
