package site.gonggangam.gonggangam_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.ResponseCode;

@Getter
@Schema(description = "데이터가 포함된 응답")
public class DataResponseDto<T> extends ResponseDto {
    private final T data;

    private DataResponseDto(T data) {
        super(true, ResponseCode.OK.getHttpStatus().value(), ResponseCode.OK.getCode(), ResponseCode.OK.getMessage());
        this.data = data;
    }

    private DataResponseDto(ResponseCode code, T data) {
        super(true, code.getHttpStatus().value(), code.getCode(), code.getMessage());
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, ResponseCode.OK.getHttpStatus().value(), ResponseCode.OK.getCode(), message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }

    public static <T> DataResponseDto<T> of(ResponseCode code, T data) {
        return new DataResponseDto<>(code, data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto<>(data, message);
    }
}
