package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum Role implements EntityEnumerable {

    USER("U", "일반 사용자"),
    ADMIN("A", "관리자");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<Role> {
        public Converter() {
            super(Role.class);
        }
    }
}
