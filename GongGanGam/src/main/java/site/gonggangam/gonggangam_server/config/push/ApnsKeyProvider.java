package site.gonggangam.gonggangam_server.config.push;

import com.turo.pushy.apns.auth.ApnsSigningKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

import java.io.InputStream;

@Component
@Slf4j
public class ApnsKeyProvider {

    @Value("${notification.apns.team-id}")
    private String teamId;

    @Value("${notification.apns.key.id}")
    private String keyId;

    @Value("${notification.apns.key.file-name}")
    private String keyFileName;

    public ApnsSigningKey getKey() throws GeneralException {
        try {
            InputStream pkcs = new ClassPathResource(keyFileName).getInputStream();
            return ApnsSigningKey.loadFromInputStream(pkcs, teamId, keyId);
        } catch (Exception e) {
            log.error(String.format("[%s] apns signing error : %s", getClass(), e));
            throw new GeneralException(ResponseCode.APNS_SIGNING_ERROR);
        }
    }
}
