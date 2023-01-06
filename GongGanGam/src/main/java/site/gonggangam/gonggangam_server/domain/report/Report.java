package site.gonggangam.gonggangam_server.domain.report;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPORT")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "REPORT_TYPE")
public class Report extends BaseTimeEntity {

    @Id
    @Column(name = "REPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "REPORTER_ID", referencedColumnName = "USER_ID")
    private Users reporter;

    @Column(name = "reason", columnDefinition = "VARCHAR(50)", length = 50)
    private String reason;

    public Report(Users reporter, String reason) {
        this.reporter = reporter;
        this.reason = reason;
    }
}
