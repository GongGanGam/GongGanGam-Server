package site.gonggangam.gonggangam_server.domain.reply;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.posts.Post;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPLY")
@Entity
@DiscriminatorValue("R")
@PrimaryKeyJoinColumn(name = "REPLY_ID")
public class Reply extends Post {

    @ManyToOne
    @JoinColumn(name = "DIARY_ID", referencedColumnName = "DIARY_ID")
    private Diary diary;

    @Builder
    public Reply(Users writer, String content, ActiveStatus activeStatus, Diary diary) {
        super(writer, content, activeStatus);
        this.diary = diary;
    }
}
