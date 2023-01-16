package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum Role implements EntityEnumerable {

    USER("U", "user",  "일반 사용자"),
    ADMIN("A", "admin",  "관리자");

    private final String key;
    private final String title;
    private final String description;

    public static class Converter extends EntityEnumerableConverter<Role> {
        public Converter() {
            super(Role.class);
        }
    }
}
