package site.gonggangam.gonggangam_server.config.push;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

import javax.net.ssl.SSLException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApnsConfig {

    private final ApnsKeyProvider apnsKeyProvider;

    @Bean
    public ApnsClient devApnsClient() throws SSLException, GeneralException {
        return getSignedApnsClient(ApnsClientBuilder.DEVELOPMENT_APNS_HOST);
    }

    @Bean
    public ApnsClient prodApnsClient() throws SSLException, GeneralException {
        return getSignedApnsClient(ApnsClientBuilder.PRODUCTION_APNS_HOST);
    }

    private ApnsClient getSignedApnsClient(String hostname) throws SSLException, GeneralException {
        try {
            return new ApnsClientBuilder()
                    .setApnsServer(hostname)
                    .setSigningKey(apnsKeyProvider.getKey())
                    .build();
        } catch (IllegalStateException e) {
            log.error(String.format("[%s] apns ssl error : %s", getClass(), e));
            throw new GeneralException(ResponseCode.APNS_AUTHENTICATION_ERROR);
        }
    }
}
