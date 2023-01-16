package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum GenderType implements EntityEnumerable {

    UNKNOWN("U", "unknown", "미선택"),
    MALE("M", "male", "남성"),
    FEMALE("F", "female", "여성");

    private final String key;
    private final String title;
    private final String description;

    public static class Converter extends EntityEnumerableConverter<GenderType> {
        public Converter() {
            super(GenderType.class);
        }
    }
}