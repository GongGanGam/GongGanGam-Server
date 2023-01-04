package site.gonggangam.gonggangam_server.domain.user_settings;

import lombok.*;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_SETTINGS")
@Entity
public class UserSettings extends BaseTimeEntity implements Serializable {

    @Id
    private Long userId;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Convert(converter = ShareType.Converter.class)
    @Column(name = "SHARE_TYPE", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ShareType shareType;


    @Column(name = "NOTIFY_DIARY", nullable = false)
    private Boolean notifyDiary;

    @Column(name = "NOTIFY_REPLY", nullable = false)
    private Boolean notifyReply;

    @Column(name = "NOTIFY_CHAT", nullable = false)
    private Boolean notifyChat;

    @Builder
    public UserSettings(Long userId, Users user, ShareType shareType, Boolean notifyDiary, Boolean notifyReply, Boolean notifyChat) {
        this.userId = userId;
        this.user = user;
        this.shareType = shareType;
        this.notifyDiary = notifyDiary;
        this.notifyReply = notifyReply;
        this.notifyChat = notifyChat;
    }

    public void update(ShareType shareType, Boolean notifyDiary, Boolean notifyReply, Boolean notifyChat) {
        this.shareType = shareType;
        this.notifyDiary = notifyDiary;
        this.notifyReply = notifyReply;
        this.notifyChat = notifyChat;
    }

}
