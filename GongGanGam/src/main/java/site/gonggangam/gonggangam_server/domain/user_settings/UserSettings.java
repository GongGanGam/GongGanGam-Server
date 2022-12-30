package site.gonggangam.gonggangam_server.domain.user_settings;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

import javax.persistence.*;

@Getter
@Builder
@RequiredArgsConstructor
@Entity
public class UserSettings extends BaseTimeEntity {

    @Id
    @JoinColumn(name = "USER_ID")
    @OneToOne
    private Users user;

    @Convert(converter = ShareType.Converter.class)
    @Column(name = "SHARE_TYPE", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ShareType shareType;

    @Column(name = "NOTIFY_DIARY", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean notifyDiary;

    @Column(name = "NOTIFY_REPLY", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean notifyReply;

    @Column(name = "NOTIFY_CHAT", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean notifyChat;


    public void update(ShareType shareType, Boolean notifyDiary, Boolean notifyReply, Boolean notifyChat) {
        this.shareType = shareType;
        this.notifyDiary = notifyDiary;
        this.notifyReply = notifyReply;
        this.notifyChat = notifyChat;
    }
}
