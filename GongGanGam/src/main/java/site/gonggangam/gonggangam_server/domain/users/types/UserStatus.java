package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum UserStatus implements EntityEnumerable {

    NORMAL("N", "normal",  "정상이용 상태"),
    SUSPENSION("S", "suspension",  "정지된 상태"),
    WITHDRAWAL("W", "withdrawal",  "탈퇴한 상태"),
    BLOCKED("B", "blocked",  "차단된 상태");

    private final String key;
    private final String title;
    private final String description;

    public static class Converter extends EntityEnumerableConverter<UserStatus> {
        public Converter() {
            super(UserStatus.class);
        }
    }

}
