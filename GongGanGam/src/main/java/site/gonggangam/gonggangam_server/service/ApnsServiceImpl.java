package site.gonggangam.gonggangam_server.service;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.config.push.PushType;

@Service
@RequiredArgsConstructor
public class ApnsServiceImpl implements ApnsService {

    private final ApnsClient apnsClient;

    @Value("${notification.topic}")
    private String topic;

    @Override
    public void notifySharedDiary(String deviceToken) {
        sendNotification(deviceToken, PushType.NOTIFY_DIARY_SHARED);
    }

    @Override
    public void notifyReply(String deviceToken) {
        sendNotification(deviceToken, PushType.NOTIFY_DIARY_REPLIED);
    }

    @Override
    public void notifyChat(String deviceToken) {
        sendNotification(deviceToken, PushType.NOTIFY_CHAT);
    }

    private void sendNotification(String deviceToken, PushType pushType) {
        SimpleApnsPushNotification notification = new SimpleApnsPushNotification(deviceToken, topic, getApnsPayload(pushType));
        apnsClient.sendNotification(notification)
                .addListener(future -> {
//                    TODO : 실패 처리
//                    future.getNow()
                });
    }

    private String getApnsPayload(PushType pushType) {
        return new ApnsPayloadBuilder()
                .setAlertTitle(pushType.getTitle())
                .setAlertBody(pushType.getBody())
                .buildWithDefaultMaximumLength();
    }
}
