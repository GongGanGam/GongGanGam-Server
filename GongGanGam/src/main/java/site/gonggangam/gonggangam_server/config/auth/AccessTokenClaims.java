package site.gonggangam.gonggangam_server.config.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenClaims {
    private final Long id;
    private final String identification;
}
