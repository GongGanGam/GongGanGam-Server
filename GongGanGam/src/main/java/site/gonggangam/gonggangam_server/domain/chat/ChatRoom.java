package site.gonggangam.gonggangam_server.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOM")
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "UUID2")
    @GenericGenerator(name = "UUID2", strategy = "UUID2")
    @Column(name = "CHAT_ROOM_ID", columnDefinition = "BINARY(16)")
    private UUID chatRoomId;

    @Column(name = "IS_VISIBLE", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private Boolean isVisible = true;

    @Builder
    public ChatRoom(Boolean isVisible) {
        this.isVisible = isVisible;
    }
}
