package site.gonggangam.gonggangam_server.domain.reply;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPLY")
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @Column(name = "REPLY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "DIARY_ID", referencedColumnName = "DIARY_ID")
    private Diary diary;

    @Column(name = "REJECTED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean rejected = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", referencedColumnName = "USER_ID")
    protected Users writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Convert(converter = ActiveStatus.Converter.class)
    @Column(name = "ACTIVE_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    protected ActiveStatus activeStatus;

    @Builder
    public Reply(Diary diary, Boolean rejected, Users writer, String content, ActiveStatus activeStatus) {
        this.diary = diary;
        this.rejected = rejected;
        this.writer = writer;
        this.content = content;
        this.activeStatus = activeStatus;
    }
}
