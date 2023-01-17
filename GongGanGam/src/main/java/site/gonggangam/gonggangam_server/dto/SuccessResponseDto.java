package site.gonggangam.gonggangam_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import site.gonggangam.gonggangam_server.config.ResponseCode;

@Getter
@Schema(description = "데이터가 포함되지 않은 성공 응답")
public class SuccessResponseDto extends ResponseDto {

    public SuccessResponseDto(ResponseCode code) {
        super(true, code.getHttpStatus().value(), code.getCode(), code.getMessage());
    }

}
