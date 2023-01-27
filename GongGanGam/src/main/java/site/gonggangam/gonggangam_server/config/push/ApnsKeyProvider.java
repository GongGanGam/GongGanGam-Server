package site.gonggangam.gonggangam_server.config.push;

import com.turo.pushy.apns.auth.ApnsSigningKey;
import lombok.extern.slf4j.Slf4j;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

@Slf4j
public class ApnsKeyProvider {

    public ApnsSigningKey getKey() throws GeneralException {
        try {
            return new ApnsSigningKey()
        } catch (Exception e) {
            log.error(String.format("[%s] apns signing error : %s", getClass(), e));
            throw new GeneralException(ResponseCode.APNS_SIGNING_ERROR);
        }
    }
}
