package site.gonggangam.gonggangam_server.domain.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum ProcessType implements EntityEnumerable {
    BEFORE("B", "처리 전"),
    PROCESSING("P", "처리 중"),
    COMPLETED("C", "처리 완료");

    private final String key;
    private final String title;

    public static class Converter extends EntityEnumerableConverter<ProcessType> {
        public Converter() {
            super(ProcessType.class);
        }
    }
}
