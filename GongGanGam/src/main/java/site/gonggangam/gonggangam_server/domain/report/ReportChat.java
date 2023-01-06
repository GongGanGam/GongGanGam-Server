package site.gonggangam.gonggangam_server.domain.report;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.chat.ChatRoom;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REPORT_CHAT")
@Entity
@DiscriminatorValue("CHAT")
public class ReportChat extends Report {

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    private ChatRoom chatRoom;

    @Builder
    public ReportChat(Users reporter, String reason, ChatRoom chatRoom) {
        super(reporter, reason);
        this.chatRoom = chatRoom;
    }
}
