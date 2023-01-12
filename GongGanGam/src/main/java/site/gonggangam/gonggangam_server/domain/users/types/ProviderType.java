package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum ProviderType implements EntityEnumerable {

    NAVER("naver", "NAVER 로그인"),
    KAKAO("kakao", "KAKAO 로그인"),
    APPLE("apple", "APPLE 로그인");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<ProviderType> {
        public Converter() {
            super(ProviderType.class);
        }
    }
}
