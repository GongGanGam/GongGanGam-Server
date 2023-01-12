package site.gonggangam.gonggangam_server.config.exceptions;

import lombok.Getter;
import site.gonggangam.gonggangam_server.dto.ResponseCode;


@Getter
public class GeneralException extends RuntimeException {
    private final ResponseCode errorCode;

    public GeneralException() {
        super(ResponseCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ResponseCode.INTERNAL_ERROR;
    }

    public GeneralException(ResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(String message) {
        super(ResponseCode.INTERNAL_ERROR.getMessage(message));
        this.errorCode = ResponseCode.INTERNAL_ERROR;
    }

}
