package site.gonggangam.gonggangam_server.service.dto.report;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {
    DIARY("diary"),
    REPLY("reply"),
    CHAT("chat");

    @JsonValue
    private final String title;
}
