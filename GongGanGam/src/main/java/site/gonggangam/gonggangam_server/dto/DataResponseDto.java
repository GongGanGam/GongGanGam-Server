package site.gonggangam.gonggangam_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "데이터가 포함된 응답")
public class DataResponseDto<T> extends ResponseDto {
    private final T data;

    private DataResponseDto(T data) {
        super(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage());
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, ResponseCode.OK.getCode(), message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto<>(data, message);
    }
}
