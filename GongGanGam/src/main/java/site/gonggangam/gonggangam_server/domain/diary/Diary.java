package site.gonggangam.gonggangam_server.domain.diary;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.posts.Post;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DIARY")
@Entity
@DiscriminatorValue("D")
@PrimaryKeyJoinColumn(name = "DIARY_ID")
public class Diary extends Post {

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    private String emoji;

    @Column(name = "IMG_URL", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "SHARE_AGREED", nullable = false)
    private Boolean shareAgreed;

    @Builder
    public Diary(Users writer, String content, ActiveStatus activeStatus, LocalDate date, String emoji, String imgUrl, Boolean shareAgreed) {
        super(writer, content, activeStatus);
        this.date = date;
        this.emoji = emoji;
        this.imgUrl = imgUrl;
        this.shareAgreed = shareAgreed;
    }
}
