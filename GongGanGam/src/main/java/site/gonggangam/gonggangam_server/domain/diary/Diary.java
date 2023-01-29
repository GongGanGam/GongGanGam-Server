package site.gonggangam.gonggangam_server.domain.diary;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "WRITING_DATE", nullable = false)
    private LocalDate writingDate;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    private String emoji;

    @Column(name = "IMG_URL", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "SHARE_AGREED", nullable = false)
    private Boolean shareAgreed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", referencedColumnName = "USER_ID")
    private Users writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "IS_VISIBLE", columnDefinition = "BIT(1) DEFAULT TRUE", nullable = false)
    private Boolean isVisible;

    @Builder
    public Diary(LocalDate writingDate, String emoji, String imgUrl, Boolean shareAgreed, Users writer, String content, Boolean isVisible) {
        this.writingDate = writingDate;
        this.emoji = emoji;
        this.imgUrl = imgUrl;
        this.shareAgreed = shareAgreed;
        this.writer = writer;
        this.content = content;
        this.isVisible = isVisible;
    }

    public void update(String emoji, String content, Boolean shareAgreed) {
        this.emoji = emoji;
        this.content = content;
        this.shareAgreed = shareAgreed;
    }

    public void delete() {
        this.isVisible = false;
    }
}
