package site.gonggangam.gonggangam_server.domain.notice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NOTICE")
@Entity
public class Notice extends BaseTimeEntity {

    @Id
    @Column(name = "NOTICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", referencedColumnName = "USER_ID")
    private Users writer;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "IS_VISIBLE", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    protected Boolean isVisible;

    @Builder
    public Notice(Users writer, String title, String content, Boolean isVisible) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.isVisible = isVisible;
    }
}
