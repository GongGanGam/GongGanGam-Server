package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum UserStatus implements EntityEnumerable {

    NORMAL("N", "정상이용 상태"),
    SUSPENSION("S", "정지된 상태"),
    WITHDRAWAL("W", "탈퇴한 상태"),
    BLOCKED("B", "차단된 상태");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<UserStatus> {
        public Converter() {
            super(UserStatus.class);
        }
    }

}
