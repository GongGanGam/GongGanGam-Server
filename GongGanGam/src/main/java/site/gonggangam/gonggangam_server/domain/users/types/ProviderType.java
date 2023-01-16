package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum ProviderType implements EntityEnumerable {

    NAVER("na", "naver",  "NAVER 로그인"),
    KAKAO("ka", "kakao",  "KAKAO 로그인"),
    APPLE("ap", "apple",  "APPLE 로그인");

    private final String key;
    private final String title;
    private final String description;

    public static class Converter extends EntityEnumerableConverter<ProviderType> {
        public Converter() {
            super(ProviderType.class);
        }
    }
}
