package site.gonggangam.gonggangam_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.ResponseCode;

@Getter
@Schema(description = "오류 발생 시 응답")
public class ErrorResponseDto extends ResponseDto {

    private ErrorResponseDto(ResponseCode code) {
        super(false, code.getHttpStatus().value(), code.getCode(), code.getMessage());
    }

    private ErrorResponseDto(ResponseCode code, Exception e) {
        super(false, code.getHttpStatus().value(), code.getCode(), code.getMessage(e));
    }

    private ErrorResponseDto(ResponseCode code, String message) {
        super(false, code.getHttpStatus().value(), code.getCode(), code.getMessage(message));
    }

    public static ErrorResponseDto of(ResponseCode code) {
        return new ErrorResponseDto(code);
    }

    public static ErrorResponseDto of(ResponseCode code, Exception e) {
        return new ErrorResponseDto(code, e);
    }

    public static ErrorResponseDto of(ResponseCode code, String message) {
        return new ErrorResponseDto(code, message);
    }
}
