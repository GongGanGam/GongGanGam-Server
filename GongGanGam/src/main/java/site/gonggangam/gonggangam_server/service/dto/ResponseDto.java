package site.gonggangam.gonggangam_server.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {
    private final Boolean success;
    private final Integer status;
    private final Integer code;
    private final String message;
}
