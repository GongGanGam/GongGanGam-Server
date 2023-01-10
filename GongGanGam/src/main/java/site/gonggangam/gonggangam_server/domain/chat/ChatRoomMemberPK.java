package site.gonggangam.gonggangam_server.domain.chat;

import java.io.Serializable;
import java.util.UUID;

public class ChatRoomMemberPK implements Serializable {
    private UUID chatRoom;
    private Long member;
}
