package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum AuthType implements EntityEnumerable {

    NAVER("NA", "NAVER 로그인"),
    KAKAO("KA", "KAKAO 로그인"),
    APPLE("AP", "APPLE 로그인");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<AuthType> {
        public Converter() {
            super(AuthType.class);
        }
    }
}
