package site.gonggangam.gonggangam_server.service;

import site.gonggangam.gonggangam_server.controller.dto.report.ReportRequestDto;
import site.gonggangam.gonggangam_server.controller.dto.report.ReportResponseDto;

import java.util.List;

public interface ReportService {

    ReportResponseDto postReport(Long userId, ReportRequestDto.PostReport body);

    List<ReportResponseDto> getReports(String type) ;

    void putReport(ReportRequestDto.PutReport body);
}