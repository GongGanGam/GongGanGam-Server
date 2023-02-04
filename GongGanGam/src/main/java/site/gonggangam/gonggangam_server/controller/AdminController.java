package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.config.HttpServletUtil;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodeGroup;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodes;
import site.gonggangam.gonggangam_server.service.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ResponseDto;
import site.gonggangam.gonggangam_server.service.dto.SuccessResponseDto;
import site.gonggangam.gonggangam_server.service.dto.notice.NoticeRequestDto;
import site.gonggangam.gonggangam_server.service.dto.notice.NoticeResponseDto;
import site.gonggangam.gonggangam_server.service.dto.report.ReportRequestDto;
import site.gonggangam.gonggangam_server.service.dto.report.ReportResponseDto;
import site.gonggangam.gonggangam_server.service.NoticeService;
import site.gonggangam.gonggangam_server.service.ReportService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "admin", description = "관리자 계정 전용 API")
@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    private final NoticeService noticeService;
    private final ReportService reportService;

    @Operation(summary = "공지사항 작성", description = "관리자 계정만 이용 가능합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "작성 성공"),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.CREATED)},
            groups = {
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @PostMapping("/notice")
    public DataResponseDto<NoticeResponseDto> postNotice(
            HttpServletRequest request,
            @RequestBody NoticeRequestDto.PostNotice body
    ) {
        return DataResponseDto.of(
                ResponseCode.CREATED,
                noticeService.postNotice(HttpServletUtil.getUserId(request), body)
        );
    }

    @Operation(summary = "공지사항 수정", description = "관리자 계정만 이용 가능합니다.")
    @PutMapping("/notice/{noticeId}")
    @ApiResponseCodes(value = {
            @ApiResponseCode(ResponseCode.OK),
            @ApiResponseCode(ResponseCode.TOKEN_EXPIRED)
    })
    public DataResponseDto<NoticeResponseDto> putNotice(
            @PathVariable("noticeId") Long noticeId,
            @RequestBody NoticeRequestDto.PutNotice body
    ) {
        return DataResponseDto.of(noticeService.putNotice(noticeId, body));
    }

    @Operation(summary = "신고 내역 조회", description = "관리자 계정만 이용 가능. 조회할 신고 타입 type = { all, diary, reply, chat }")
    @GetMapping("/report")
    public DataResponseDto<List<ReportResponseDto>> getReport(
            @Schema(defaultValue = "diary")
            @RequestParam("type") String type
    ) {
        return DataResponseDto.of(reportService.getReports(type));
    }

    @Operation(summary = "신고 처리상태 변경", description = "신고 내역의 처리 상태를 변경합니다. 관리자 계정만 이용 가능합니다. 처리 상태 progress = { before, processing, completed }")
    @PutMapping("/report/{reportId}")
    @ApiResponseCodes(value = {
            @ApiResponseCode(ResponseCode.OK)
    })
    public ResponseDto putReport(
            @RequestBody ReportRequestDto.PutReport body
    ) {
        reportService.putReport(body);
        return new SuccessResponseDto(ResponseCode.OK);
    }
}
