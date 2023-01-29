package site.gonggangam.gonggangam_server.config.push;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushType {

    NOTIFY_DIARY_SHARED("공유받은 일기가 있어요.", "공유받은 일기를 지금 확인해보세요."),
    NOTIFY_DIARY_REPLIED("일기에 답장이 생겼어요.", "누군가 당신의 일기에 답장을 달았어요."),
    NOTIFY_CHAT("새로운 채팅 알림", "채팅이 달렸어요.");

    private final String title;
    private final String body;
}
