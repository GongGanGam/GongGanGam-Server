package site.gonggangam.gonggangam_server.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public abstract Long getId();
    public abstract String getEmail();
    public abstract String getNickname();
}
