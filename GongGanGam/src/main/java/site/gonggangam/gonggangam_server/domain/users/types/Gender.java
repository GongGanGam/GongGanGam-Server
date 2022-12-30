package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum Gender implements EntityEnumerable {

    UNKNOWN("U", "미선택"),
    MALE("M", "남성"),
    FEMALE("F", "여성");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<Gender> {
        public Converter() {
            super(Gender.class);
        }
    }
}