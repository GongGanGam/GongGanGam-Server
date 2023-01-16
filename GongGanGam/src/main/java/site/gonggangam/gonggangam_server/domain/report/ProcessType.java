package site.gonggangam.gonggangam_server.domain.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.EntityEnumerable;
import site.gonggangam.gonggangam_server.config.EntityEnumerableConverter;

@Getter
@AllArgsConstructor
public enum ProcessType implements EntityEnumerable {
    BEFORE("B", "before", "처리 전"),
    PROCESSING("P", "processing",  "처리 중"),
    COMPLETED("C", "completed",  "처리 완료");

    private final String key;
    private final String title;
    private final String description;

    public static class Converter extends EntityEnumerableConverter<ProcessType> {
        public Converter() {
            super(ProcessType.class);
        }
    }
}
