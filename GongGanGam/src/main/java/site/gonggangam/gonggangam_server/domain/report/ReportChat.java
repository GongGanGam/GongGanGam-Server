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
    @JoinColumn(name = "CHAT_ROOM_ID", referencedColumnName = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    @Builder
    public ReportChat(Users reporter, String reason, ProcessType processType, ChatRoom chatRoom) {
        super(reporter, reason, processType);
        this.chatRoom = chatRoom;
    }
}
