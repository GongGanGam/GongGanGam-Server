package site.gonggangam.gonggangam_server.domain.report;

import lombok.*;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@Table(name = "REPORT")
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "REPORT_TYPE")
public class Report extends BaseTimeEntity {

    @Id
    @Column(name = "REPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER_ID", referencedColumnName = "USER_ID")
    private Users reporter;

    @Column(name = "REASON", columnDefinition = "VARCHAR(50)", length = 50)
    private String reason;

    @Convert(converter = ProcessType.Converter.class)
    @Column(name = "PROGRESS", columnDefinition = "CHAR(1) DEFAULT 'B'", length = 1)
    private ProcessType processType = ProcessType.BEFORE;

    public Report(Users reporter, String reason, ProcessType processType) {
        this.reporter = reporter;
        this.reason = reason;
        this.processType = processType;
    }
}
