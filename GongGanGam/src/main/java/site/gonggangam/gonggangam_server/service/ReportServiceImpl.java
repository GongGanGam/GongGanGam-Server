package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.domain.report.ProcessType;
import site.gonggangam.gonggangam_server.domain.report.Report;
import site.gonggangam.gonggangam_server.domain.report.ReportDiary;
import site.gonggangam.gonggangam_server.domain.report.ReportReply;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.dto.report.*;
import site.gonggangam.gonggangam_server.repository.DiaryRepository;
import site.gonggangam.gonggangam_server.repository.ReplyRepository;
import site.gonggangam.gonggangam_server.repository.ReportRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final UsersRepository usersRepository;
    private final DiaryRepository diaryRepository;
    private final ReplyRepository replyRepository;

    private final ReportRepository reportRepository;

    // TODO : 채팅 신고 구현
    @Override
    @Transactional
    public ReportResponseDto postReport(Long userId, ReportRequestDto.PostReport body) throws GeneralException {
        Users reporter = usersRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND_USER));

        if (body.getType() == ReportType.DIARY) {
            Diary diary = diaryRepository.findById(body.getTargetId())
                    .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND));

            ReportDiary report = ReportDiary.builder()
                    .diary(diary)
                    .reporter(reporter)
                    .processType(ProcessType.BEFORE)
                    .reason(body.getReason())
                    .build();

            reportRepository.save(report);

            return DiaryReportResponseDto.toDto(report);
        }
        else if (body.getType() == ReportType.REPLY) {
            Reply reply = replyRepository.findById(body.getTargetId())
                    .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND));

            ReportReply report = ReportReply.builder()
                    .reply(reply)
                    .reporter(reporter)
                    .processType(ProcessType.BEFORE)
                    .reason(body.getReason())
                    .build();

            reportRepository.save(report);

            return ReplyReportResponseDto.toDto(report);
        }

        throw new GeneralException(ResponseCode.BAD_REQUEST);
    }

    @Override
    public List<ReportResponseDto> getReports(String type) {
        // TODO : type 구분
        return reportRepository.findAll().stream()
                .map(entity -> {
                    if (entity instanceof ReportDiary) {
                        return DiaryReportResponseDto.toDto((ReportDiary) entity);
                    }
                    else if (entity instanceof ReportReply) {
                        return ReplyReportResponseDto.toDto((ReportReply) entity);
                    }
                    throw new GeneralException(ResponseCode.INTERNAL_ERROR);
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void putReport(ReportRequestDto.PutReport body) throws GeneralException {
        Report report = reportRepository.findById(body.getReportId())
                .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND));

        report.updateProcess(convert(body.getProgress()));
        reportRepository.save(report);
    }

    private static ProcessType convert(String process) throws GeneralException {
        return switch (process) {
            case "before" -> ProcessType.BEFORE;
            case "processing" -> ProcessType.PROCESSING;
            case "completed" -> ProcessType.COMPLETED;
            default -> throw new GeneralException(ResponseCode.BAD_REQUEST);
        };
    }
}
