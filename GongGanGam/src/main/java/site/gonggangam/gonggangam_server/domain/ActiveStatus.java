package site.gonggangam.gonggangam_server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum ActiveStatus implements EntityEnumerable {

    INACTIVE("I", "INACTIVE"),
    ACTIVE("A", "ACTIVE");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<ActiveStatus> {
        public Converter() {
            super(ActiveStatus.class);
        }
    }

}