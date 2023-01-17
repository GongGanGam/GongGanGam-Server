package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.notice.Notice;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.dto.notice.NoticeRequestDto;
import site.gonggangam.gonggangam_server.dto.notice.NoticeResponseDto;
import site.gonggangam.gonggangam_server.repository.NoticeRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final UsersRepository usersRepository;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public NoticeResponseDto postNotice(Long userId, NoticeRequestDto.PostNotice request) throws GeneralException {
        Users writer = usersRepository.findById(userId).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND_USER);
        });

        Notice notice = Notice.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .writer(writer)
                .isVisible(true)
                .build();

        noticeRepository.save(notice);
        return NoticeResponseDto.of(notice);
    }

    @Override
    @Transactional
    public NoticeResponseDto putNotice(Long noticeId, NoticeRequestDto.PutNotice request) throws GeneralException {
        Notice notice = noticeRepository.findByNoticeIdAndIsVisible(noticeId, true).orElseThrow(() -> {
            throw new GeneralException(ResponseCode.NOT_FOUND);
        });

        notice.update(request.getTitle(), request.getContent());
        noticeRepository.save(notice);

        return NoticeResponseDto.of(notice);
    }

    @Override
    public List<NoticeResponseDto> getNoticeList(Integer page, Integer pageSize) {
        // TODO : paging 구현
        List<Notice> notices = noticeRepository.findAllByIsVisible(true);

        return notices.stream()
                .map(NoticeResponseDto::of)
                .collect(Collectors.toList());
    }
}
