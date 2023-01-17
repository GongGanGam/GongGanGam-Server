package site.gonggangam.gonggangam_server.config;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EntityEnumerable {

    String getKey();

    @JsonValue
    String getTitle();

    String getDescription();

}
