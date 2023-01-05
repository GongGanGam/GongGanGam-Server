package site.gonggangam.gonggangam_server.domain.diary;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DIARY")
@Entity
public class Diary extends BaseTimeEntity {

    @Id
    @Column(name = "DIARY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    private String emoji;

    @Column(name = "IMG_URL", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "SHARE_AGREED", nullable = false)
    private Boolean shareAgreed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", referencedColumnName = "USER_ID")
    protected Users writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Convert(converter = ActiveStatus.Converter.class)
    @Column(name = "ACTIVE_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    protected ActiveStatus activeStatus;

    @Builder
    public Diary(LocalDate date, String emoji, String imgUrl, Boolean shareAgreed, Users writer, String content, ActiveStatus activeStatus) {
        this.date = date;
        this.emoji = emoji;
        this.imgUrl = imgUrl;
        this.shareAgreed = shareAgreed;
        this.writer = writer;
        this.content = content;
        this.activeStatus = activeStatus;
    }
}
