package site.gonggangam.gonggangam_server.domain.diary;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SHARE_DIARY")
@IdClass(ShareDiaryPK.class)
@Entity
public class ShareDiary extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIARY_ID", referencedColumnName = "DIARY_ID")
    private Diary diary;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private Users receiver;

    @Builder
    public ShareDiary(Diary diary, Users receiver) {
        this.diary = diary;
        this.receiver = receiver;
    }
}
