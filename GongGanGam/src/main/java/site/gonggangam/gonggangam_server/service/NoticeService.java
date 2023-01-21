package site.gonggangam.gonggangam_server.service;

import org.springframework.data.domain.Pageable;
import site.gonggangam.gonggangam_server.dto.notice.NoticeRequestDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeResponseDto;

import java.util.List;

public interface NoticeService {

    NoticeResponseDto postNotice(Long userId, NoticeRequestDto.PostNotice request);

    NoticeResponseDto putNotice(Long noticeId, NoticeRequestDto.PutNotice request);

    List<NoticeResponseDto> getNoticeList(Pageable pageable);
}
