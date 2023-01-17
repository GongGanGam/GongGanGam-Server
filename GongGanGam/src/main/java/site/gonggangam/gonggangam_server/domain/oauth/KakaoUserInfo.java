package site.gonggangam.gonggangam_server.domain.oauth;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Long getId() {
        return Long.parseLong(String.valueOf(attributes.get("id")));
    }

    @Override
    public String getEmail() {
        return (String) getKakaoAccount().get("email");
    }

    @Override
    public String getNickname() {
        return (String) getKakaoAccount().get("nickname");
    }

    public Map<String, Object> getKakaoAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile() {
        return (Map<String, Object>) getKakaoAccount().get("profile");
    }

}
