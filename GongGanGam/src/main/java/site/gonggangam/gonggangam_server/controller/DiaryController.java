package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.gonggangam.gonggangam_server.config.HttpServletUtil;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodeGroup;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodes;
import site.gonggangam.gonggangam_server.service.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.service.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.service.dto.SuccessResponseDto;
import site.gonggangam.gonggangam_server.service.dto.diary.CalendarResponseDto;
import site.gonggangam.gonggangam_server.service.dto.diary.DiaryRequestDto;
import site.gonggangam.gonggangam_server.service.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.service.dto.diary.SharedDiaryResponseDto;
import site.gonggangam.gonggangam_server.service.DiaryService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "diary", description = "일기 관련 API")
@RestController
@RequestMapping(value = "/api/diary", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "일기 작성", description = "원하는 날짜에 일기를 작성합니다. (스웨거에서 file 첨부를 하지 않을 때는 'send empty value' 체크를 해제해주세요.)")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.CREATED),
                    @ApiResponseCode(ResponseCode.DIARY_DATE_CONFLICT),
                    @ApiResponseCode(ResponseCode.DIARY_DATE_INVALID)
            },
            groups = {
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public DataResponseDto<DiaryResponseDto> postDiary(
            HttpServletRequest request,
            @Parameter(description = "일기 상세 내용을 입력 받습니다.", content = @Content(schema = @Schema(implementation = DiaryRequestDto.PostDiary.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            @ModelAttribute DiaryRequestDto.PostDiary body
            ) {

        return DataResponseDto.of(
                diaryService.postDiary(HttpServletUtil.getUserId(request), body)
        );
    }

    @Operation(summary = "공유받은 일기 목록", description = "다른 사용자에게 공유 받은 일기 목록을 조회합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @GetMapping("/shared")
    public DataResponseDto<Page<SharedDiaryResponseDto>> getSharedDiaries(
            HttpServletRequest request,
            @ParameterObject Pageable pageable
    ) {

        return DataResponseDto.of(
                diaryService.getSharedDiaries(HttpServletUtil.getUserId(request), pageable)
        );
    }

    @Operation(summary = "나의 일기 상세조회", description = "일기의 내용을 상세조회합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @GetMapping("/{diaryId}")
    public DataResponseDto<DiaryResponseDto> getDiary(
            @PathVariable("diaryId") Long diaryId
    ) {
        return DataResponseDto.of(diaryService.getDiary(diaryId));
    }

    @Operation(summary = "캘린더에서 일기 목록 조회하기", description = "연도, 월에 해당하는 일기 목록을 조회합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK),
                    @ApiResponseCode(ResponseCode.BAD_REQUEST)
            },
            groups = {
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @GetMapping
    public DataResponseDto<CalendarResponseDto> getDiaries(
            HttpServletRequest request,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        return DataResponseDto.of(
                diaryService.getDiaries(HttpServletUtil.getUserId(request), year, month)
        );
    }

    @Operation(summary = "일기 수정", description = "작성된 일기의 내용을 수정합니다. (스웨거에서 file 첨부를 하지 않을 때는 'send empty value' 체크를 해제해주세요.)")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @PutMapping(value = "/{diaryId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public DataResponseDto<DiaryResponseDto> putDiary(
            @PathVariable("diaryId") Long diaryId,
            @Parameter(description = "일기 상세 내용", content = @Content(schema = @Schema(implementation = DiaryRequestDto.PutDiary.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            @ModelAttribute DiaryRequestDto.PutDiary body
    ) {
        return DataResponseDto.of(diaryService.putDiary(diaryId, body));
    }

    @Operation(summary = "일기 삭제", description = "일기를 삭제합니다.")
    @ApiResponseCodes(
            value = {
                    @ApiResponseCode(ResponseCode.OK)
            },
            groups = {
                    ApiResponseCodeGroup.OPTIONAL,
                    ApiResponseCodeGroup.AUTHENTICATED
            }
    )
    @DeleteMapping("/{diaryId}")
    public SuccessResponseDto deleteDiary(
            @PathVariable("diaryId") Long diaryId
    ) {
        diaryService.deleteDiary(diaryId);
        return new SuccessResponseDto(ResponseCode.OK);
    }

}
