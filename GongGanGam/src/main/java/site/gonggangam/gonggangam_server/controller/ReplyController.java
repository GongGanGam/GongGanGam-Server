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
import site.gonggangam.gonggangam_server.controller.dto.DataResponseDto;
import site.gonggangam.gonggangam_server.controller.dto.ErrorResponseDto;
import site.gonggangam.gonggangam_server.controller.dto.ResponseDto;
import site.gonggangam.gonggangam_server.controller.dto.SuccessResponseDto;
import site.gonggangam.gonggangam_server.controller.dto.reply.ReplyPreviewResponseDto;
import site.gonggangam.gonggangam_server.controller.dto.reply.ReplyRequestDto;
import site.gonggangam.gonggangam_server.controller.dto.reply.ReplyResponseDto;
import site.gonggangam.gonggangam_server.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "reply", description = "일기 답장 관련 API")
@RestController
@RequestMapping(value = "/api/reply", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @Operation(summary = "답장 상세조회", description = "답장 내용과 원본 일기 내용을 상세조회합니다.")
    @GetMapping("/{replyId}")
    public DataResponseDto<ReplyResponseDto> getReply(
            @PathVariable("replyId") Long replyId
    ) {
        return DataResponseDto.of(replyService.getReply(replyId));
    }

    @Operation(summary = "받은 답장목록 조회", description = "받은 답장 목록을 조회합니다. ")
    @GetMapping
    public DataResponseDto<List<ReplyPreviewResponseDto>> getReplies(
            HttpServletRequest request,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
            ) {
        return DataResponseDto.of(
                replyService.getReplies(HttpServletUtil.getUserId(request), page, pageSize)
        );
    }

    @Operation(summary = "답장 작성하기", description = "공유받은 일기에 답장을 작성합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "작성 성공"),
                    @ApiResponse(responseCode = "400", description = "이미 답장을 작성한 일기입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @PostMapping
    public DataResponseDto<ReplyResponseDto> postReply(
            @RequestBody ReplyRequestDto.Post body
            ) {

        return DataResponseDto.of(replyService.postReply(1L, body));
    }

    @Operation(summary = "답장 삭제하기", description = "작성한 답장을 삭제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "삭제 성공"),
                    @ApiResponse(responseCode = "401", description = "인증에 실패했습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "만료된 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "올바르지 않은 경로입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    @DeleteMapping("/{replyId}")
    public ResponseDto deleteReply(
            @PathVariable("replyId") Long replyId
    ) {
        replyService.deleteReply(replyId);
        return new SuccessResponseDto(ResponseCode.OK);
    }

    @Operation(summary = "답장 수정하기", description = "작성한 답장의 내용을 수정합니다.")
    @PutMapping("/{replyId}")
    public DataResponseDto<ReplyResponseDto> putReply(
            @PathVariable("replyId") Long replyId,
            @RequestBody ReplyRequestDto.Put body
    ) {
        return DataResponseDto.of(replyService.putReply(replyId, body));
    }

    @Operation(summary = "답장 거절하기", description = "수신된 답장을 거절합니다. 거절된 답장은 이후 목록에서 볼 수 없습니다.")
    @PutMapping("/{replyId}/reject")
    public ResponseDto rejectReply(
            @PathVariable("replyId") Long replyId
    ) {
        replyService.rejectReply(replyId);
        return new SuccessResponseDto(ResponseCode.OK);
    }
}
