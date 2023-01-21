package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeResponseDto;
import site.gonggangam.gonggangam_server.service.NoticeService;

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
//            @RequestParam(value = "page") Integer page,
//            @RequestParam(value = "pageSize") Integer pageSize,
//            @ParameterObject
//            @ParameterObject
//            @PageableDefault(size = 5, sort = {"createdAt", "ASC"})
            @ParameterObject
            Pageable pageable
    ) {
        return DataResponseDto.of(noticeService.getNoticeList(pageable));
    }

}
