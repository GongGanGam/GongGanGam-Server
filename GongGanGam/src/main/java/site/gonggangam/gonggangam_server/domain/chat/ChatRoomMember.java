package site.gonggangam.gonggangam_server.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOM_MEMBER")
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(ChatRoomMemberPK.class)
public class ChatRoomMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID", referencedColumnName = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "USER_ID")
    private Users member;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public ChatRoomMember(ChatRoom chatRoom, Users member) {
        this.chatRoom = chatRoom;
        this.member = member;
    }
}
