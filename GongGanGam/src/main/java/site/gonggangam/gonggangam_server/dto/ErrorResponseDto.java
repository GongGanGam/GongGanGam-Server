package site.gonggangam.gonggangam_server.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto extends ResponseDto {

    private ErrorResponseDto(ResponseCode code) {
        super(false, code.getCode(), code.getMessage());
    }

    private ErrorResponseDto(ResponseCode code, Exception e) {
        super(false, code.getCode(), code.getMessage(e));
    }

    private ErrorResponseDto(ResponseCode code, String message) {
        super(false, code.getCode(), code.getMessage(message));
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
