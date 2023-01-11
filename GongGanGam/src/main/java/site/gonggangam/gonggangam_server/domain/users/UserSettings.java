package site.gonggangam.gonggangam_server.domain.users;

import lombok.*;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_SETTINGS")
@Entity
public class UserSettings extends BaseTimeEntity implements Serializable {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Convert(converter = ShareType.Converter.class)
    @Column(name = "SHARE_TYPE", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ShareType shareType;


    @Column(name = "NOTIFY_DIARY", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private Boolean notifyDiary = true;

    @Column(name = "NOTIFY_REPLY", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private Boolean notifyReply = true;

    @Column(name = "NOTIFY_CHAT", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private Boolean notifyChat = true;

    @Builder
    public UserSettings(Users user, ShareType shareType, Boolean notifyDiary, Boolean notifyReply, Boolean notifyChat) {
        this.user = user;
        this.shareType = shareType;
        this.notifyDiary = notifyDiary;
        this.notifyReply = notifyReply;
        this.notifyChat = notifyChat;
    }

    public void updateShareType(ShareType shareType) {
        this.shareType = shareType;
    }

    public void updateNotify(Boolean notifyDiary, Boolean notifyReply, Boolean notifyChat) {
        this.notifyDiary = notifyDiary;
        this.notifyReply = notifyReply;
        this.notifyChat = notifyChat;
    }

}
