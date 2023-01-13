package site.gonggangam.gonggangam_server.config;

import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
@RequiredArgsConstructor
public abstract class EntityEnumerableConverter<T extends EntityEnumerable> implements AttributeConverter<T, String> {

    private final Class<T> clazz;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (Objects.isNull(attribute))
            return null;
        return attribute.getKey();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (StringUtils.isNullOrEmpty(dbData))
            return null;
        T[] enumConstants = clazz.getEnumConstants();

        for (T constant : enumConstants) {
            if (constant.getKey().equals(dbData))
                return constant;
        }

        throw new UnsupportedOperationException("지원하지 않는 enum 형식입니다. : " + dbData);
    }

}
