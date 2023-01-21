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
import site.gonggangam.gonggangam_server.config.HttpServletUtils;
import site.gonggangam.gonggangam_server.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.dto.ResponseDto;
import site.gonggangam.gonggangam_server.dto.SuccessResponseDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeRequestDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeResponseDto;
import site.gonggangam.gonggangam_server.service.NoticeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "notice", description = "공지사항 관련 API")
@RestController
@RequestMapping(value = "/api/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 조회", description = "공지사항 목록을 조회합니다.")
    @GetMapping
    public DataResponseDto<List<NoticeResponseDto>> getNoticeList(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
    ) {
        return DataResponseDto.of(noticeService.getNoticeList(page, pageSize));
    }

}
