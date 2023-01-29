package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.service.dto.reply.ReplyPreviewResponseDto;
import site.gonggangam.gonggangam_server.service.dto.reply.ReplyRequestDto;
import site.gonggangam.gonggangam_server.service.dto.reply.ReplyResponseDto;

import java.util.List;

public interface ReplyService {

    ReplyResponseDto getReply(Long replyId);

    List<ReplyPreviewResponseDto> getReplies(Long userId, Integer page, Integer pageSize);

    ReplyResponseDto postReply(Long userId, ReplyRequestDto.Post body);

    ReplyResponseDto putReply(Long replyId, ReplyRequestDto.Put body);

    void rejectReply(Long replyId);

    void deleteReply(Long replyId);
}
