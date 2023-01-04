package site.gonggangam.gonggangam_server.domain.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOM")
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;


}
