package site.gonggangam.gonggangam_server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    OK(2000, HttpStatus.OK, "요청이 완료되었습니다."),

    BAD_REQUEST(4000, HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다."),
    VALIDATION_ERROR(4001, HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),

    TOKEN_INVALID(4010, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(4011, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_IS_NULL(4012, HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    TOKEN_CANT_NOT_DECODE(4013, HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식입니다."),
    TOKEN_USER_INVALID(4014, HttpStatus.UNAUTHORIZED, "이용할 수 없는 사용자입니다."),

    PERMISSION_DENIED(4030, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    NOT_FOUND(4040, HttpStatus.NOT_FOUND, "요청과 일치하는 자원이 없습니다."),
    NOT_FOUND_USER(4041, HttpStatus.NOT_FOUND, "일치하는 사용자가 없습니다."),

    INTERNAL_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    DATA_ACCESS_ERROR(5001, HttpStatus.INTERNAL_SERVER_ERROR, "데이터 접근 중 오류가 발생했습니다.");

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static ResponseCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(code -> code.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) return ResponseCode.BAD_REQUEST;
                    else if (httpStatus.is5xxServerError()) return ResponseCode.INTERNAL_ERROR;
                    return ResponseCode.OK;
                });
    }


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
