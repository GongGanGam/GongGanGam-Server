package site.gonggangam.gonggangam_server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.gonggangam.gonggangam_server.dto.reply.ReplyResponseDto;

@Tag(name = "reply", description = "일기 답장 관련 API")
@RestController
@RequestMapping(value = "/api/reply", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReplyController {

    @Operation(summary = "답장 상세조회", description = "답장 내용과 원본 일기 내용을 상세조회합니다.")
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyResponseDto> getReply(
            @PathVariable("replyId") Long replyId
    ) {
        return null;
    }
}
