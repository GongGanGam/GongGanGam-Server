package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.dto.ResponseDto;
import site.gonggangam.gonggangam_server.dto.SuccessResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.CalendarResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryRequestDto;
import site.gonggangam.gonggangam_server.dto.diary.DiaryResponseDto;
import site.gonggangam.gonggangam_server.dto.diary.SharedDiaryResponseDto;
import site.gonggangam.gonggangam_server.service.DiaryService;

import java.util.List;

@Tag(name = "diary", description = "일기 관련 API")
@RestController
@RequestMapping(value = "/api/diary", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor

// TODO : userid
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "일기 작성", description = "원하는 날짜에 일기를 작성합니다. request body 형식은 application/json을 선택해서 참조해주세요.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "작성 성공"),
                    @ApiResponse(responseCode = "400", description = "이미 일기가 작성된 날짜입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public DataResponseDto<DiaryResponseDto> postDiary(
            @Parameter(description = "일기에 업로드할 이미지 파일을 multipart/form-data 형식으로 받습니다.", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
            @Parameter(description = "일기 상세 내용을 application/json 형식으로 입력 받습니다.", content = @Content(schema = @Schema(implementation = DiaryRequestDto.Post.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            @ModelAttribute DiaryRequestDto.Post data
            ) {

        return DataResponseDto.of(diaryService.postDiary(1L, data));
    }

    @Operation(summary = "공유받은 일기 목록", description = "다른 사용자에게 공유 받은 일기 목록을 조회합니다.")
    @GetMapping("/shared")
    public DataResponseDto<List<SharedDiaryResponseDto>> getSharedDiaries(
            Pageable pageable
    ) {

        return DataResponseDto.of(diaryService.getSharedDiaries(1L, pageable));
    }

    @Operation(summary = "나의 일기 상세조회", description = "일기의 내용을 상세조회합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "올바르지 않은 경로입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @GetMapping("/{diaryId}")
    public DataResponseDto<DiaryResponseDto> getDiary(
            @PathVariable("diaryId") Long diaryId
    ) {
        return DataResponseDto.of(diaryService.getDiary(diaryId));
    }

    @Operation(summary = "캘린더에서 일기 목록 조회하기", description = "연도, 월에 해당하는 일기 목록을 조회합니다.")
    @GetMapping
    public DataResponseDto<CalendarResponseDto> getDiaries(
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        return DataResponseDto.of(diaryService.getDiaries(1L, year, month));
    }

    @Operation(summary = "일기 수정", description = "작성된 일기의 내용을 수정합니다. request body 형식은 application/json을 선택해서 참조해주세요.")
    @PutMapping(value = "/{diaryId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public DataResponseDto<DiaryResponseDto> putDiary(
            @Parameter(description = "일기에 업로드할 이미지 파일을 multipart/form-data 형식으로 받습니다.", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
            @Parameter(description = "일기 상세 내용을 application/json 형식으로 입력 받습니다.", content = @Content(schema = @Schema(implementation = DiaryRequestDto.Put.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            @ModelAttribute DiaryRequestDto.Put data
    ) {
        return DataResponseDto.of(diaryService.putDiary(data));
    }

    @Operation(summary = "일기 삭제", description = "일기를 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "삭제 성공"),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "올바르지 않은 경로입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
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
