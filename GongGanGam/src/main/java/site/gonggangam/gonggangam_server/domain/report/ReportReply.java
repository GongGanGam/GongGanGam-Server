package site.gonggangam.gonggangam_server.domain.report;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.reply.Reply;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPORT_REPLY")
@Entity
@DiscriminatorValue("REPLY")
public class ReportReply extends Report {

    @ManyToOne
    @JoinColumn(name = "REPLY_ID", referencedColumnName = "REPLY_ID")
    private Reply reply;

    @Builder
    public ReportReply(Users reporter, String reason, Reply reply) {
        super(reporter, reason);
        this.reply = reply;
    }
}
