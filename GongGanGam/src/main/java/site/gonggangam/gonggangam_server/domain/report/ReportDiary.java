package site.gonggangam.gonggangam_server.domain.report;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPORT_DIARY")
@Entity
@DiscriminatorValue("DIARY")
public class ReportDiary extends Report {

    @ManyToOne
    @JoinColumn(name = "DIARY_ID", referencedColumnName = "DIARY_ID")
    private Diary diary;

    @Builder
    public ReportDiary(Users reporter, String reason, ProcessType processType, Diary diary) {
        super(reporter, reason, processType);
        this.diary = diary;
    }
}
