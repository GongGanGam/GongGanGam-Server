package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import site.gonggangam.gonggangam_server.service.dto.report.ReportRequestDto;
import site.gonggangam.gonggangam_server.service.dto.report.ReportResponseDto;
import site.gonggangam.gonggangam_server.service.ReportService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "report", description = "신고 관련 API")
@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "신고 등록", description = "type = { diary, reply, chat }. chat일 경우 chatRoomId를 입력해주세요.")
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
    @PostMapping
    public DataResponseDto<ReportResponseDto> postReport(
            HttpServletRequest request,
            @RequestBody ReportRequestDto.PostReport body
            ) {
        return DataResponseDto.of(
                reportService.postReport(HttpServletUtil.getUserId(request), body)
        );
    }

}
