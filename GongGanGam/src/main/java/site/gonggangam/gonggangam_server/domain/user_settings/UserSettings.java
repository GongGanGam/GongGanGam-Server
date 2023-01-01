package site.gonggangam.gonggangam_server.domain.user_settings;

import lombok.*;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_SETTINGS")
@Entity
public class UserSettings extends BaseTimeEntity implements Serializable {

    @Id
    private Long userId;

    @Convert(converter = ShareType.Converter.class)
    @Column(name = "SHARE_TYPE", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ShareType shareType;


    @Column(name = "NOTIFY_DIARY", nullable = false)
    private Boolean notifyDiary;

    @Column(name = "NOTIFY_REPLY", nullable = false)
    private Boolean notifyReply;

    @Column(name = "NOTIFY_CHAT", nullable = false)
    private Boolean notifyChat;

    @MapsId("userId")
    @OneToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private Users user;

    public void update(ShareType shareType, Boolean notifyDiary, Boolean notifyReply, Boolean notifyChat) {
        this.shareType = shareType;
        this.notifyDiary = notifyDiary;
        this.notifyReply = notifyReply;
        this.notifyChat = notifyChat;
    }

}
