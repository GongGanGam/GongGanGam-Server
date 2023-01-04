package site.gonggangam.gonggangam_server.domain.report;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.posts.Post;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPORT")
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @Column(name = "REPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "REPORTER_ID", referencedColumnName = "USER_ID")
    private Users reporter;

    @ManyToOne
    @JoinColumn(name = "TARGET_ID", referencedColumnName = "POST_ID")
    private Post target;

    @Column(name = "reason", columnDefinition = "VARCHAR(50)", length = 50)
    private String reason;

    @Builder
    public Report(Users reporter, Post target, String reason) {
        this.reporter = reporter;
        this.target = target;
        this.reason = reason;
    }
}
