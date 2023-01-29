package site.gonggangam.gonggangam_server.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.gonggangam.gonggangam_server.service.dto.notice.NoticeRequestDto;
import site.gonggangam.gonggangam_server.service.dto.notice.NoticeResponseDto;


public interface NoticeService {

    NoticeResponseDto postNotice(Long userId, NoticeRequestDto.PostNotice request);

    NoticeResponseDto putNotice(Long noticeId, NoticeRequestDto.PutNotice request);

    Page<NoticeResponseDto> getNoticeList(Pageable pageable);
}
