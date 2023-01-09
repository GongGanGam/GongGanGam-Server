package site.gonggangam.gonggangam_server.domain.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_MESSAGE")
@Entity
public class ChatMessage extends BaseTimeEntity {

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID", referencedColumnName = "ROOM_ID")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID")
    private Users sender;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;
}
