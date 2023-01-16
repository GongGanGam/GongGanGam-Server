package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum ShareType implements EntityEnumerable {

    ALL("A", "all",  "전체 사용자 대상 공유"),
    SIMILAR("S", "similar",  "비슷한 연령대 공유");

    private final String key;
    private final String title;
    private final String description;

    @javax.persistence.Converter
    public static class Converter extends EntityEnumerableConverter<ShareType> {
        public Converter() {
            super(ShareType.class);
        }
    }
}