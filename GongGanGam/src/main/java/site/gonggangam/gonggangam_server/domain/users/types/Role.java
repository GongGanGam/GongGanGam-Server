package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum Role implements EntityEnumerable, GrantedAuthority {

    USER("U", "ROLE_USER",  "일반 사용자"),
    ADMIN("A", "ROLE_ADMIN",  "관리자");

    private final String key;
    private final String title;
    private final String description;

    @Override
    public String getAuthority() {
        return this.title;
    }

    public static class Converter extends EntityEnumerableConverter<Role> {
        public Converter() {
            super(Role.class);
        }
    }
}
