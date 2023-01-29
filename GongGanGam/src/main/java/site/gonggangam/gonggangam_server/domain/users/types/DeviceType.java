package site.gonggangam.gonggangam_server.domain.users.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum DeviceType implements EntityEnumerable {

    IOS("I", "ios", "ios, ipad os 기기"),
    ANDROID("A", "android", "android 기기");

    private final String key;
    private final String title;
    private final String description;

    public static class Converter extends EntityEnumerableConverter<DeviceType> {
        public Converter() {
            super(DeviceType.class);
        }
    }
}
