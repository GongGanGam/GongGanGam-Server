package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeResponseDto;
import site.gonggangam.gonggangam_server.service.NoticeService;

@Tag(name = "notice", description = "공지사항 관련 API")
@RestController
@RequestMapping(value = "/api/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 조회", description = "공지사항 목록을 조회합니다.")
    @GetMapping
    public DataResponseDto<Page<NoticeResponseDto>> getNoticeList(
            @ParameterObject
            Pageable pageable
    ) {
        return DataResponseDto.of(noticeService.getNoticeList(pageable));
    }

}
