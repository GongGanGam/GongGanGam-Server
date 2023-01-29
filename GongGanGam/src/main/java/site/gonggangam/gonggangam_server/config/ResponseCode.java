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
    OK(20000, HttpStatus.OK, "요청이 완료되었습니다."),
    CREATED(20100, HttpStatus.CREATED, "요청이 완료되었습니다."),

    BAD_REQUEST(-40000, HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다."),
    VALIDATION_ERROR(-40001, HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    REQUIRE_VALUE(-40002, HttpStatus.BAD_REQUEST, "누락된 값이 있습니다."),
    WRONG_OAUTH_TOKEN(-40003, HttpStatus.BAD_REQUEST, "소셜로그인에 필요한 토큰이 잘못되었습니다."),
    REQUIRE_OAUTH_EMAIL(-40004, HttpStatus.BAD_REQUEST, "소셜로그인의 이메일 제공 동의가 필요합니다."),

    TOKEN_INVALID(-40100, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(-40101, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_IS_NULL(-40102, HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    TOKEN_CANT_NOT_DECODE(-40103, HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식입니다."),
    AUTHENTICATION_INVALID_USER(-40104, HttpStatus.UNAUTHORIZED, "이용할 수 없는 사용자입니다."),

    PERMISSION_DENIED(-40300, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    REQUIRED_SIGNUP(-40301, HttpStatus.FORBIDDEN, "추가 회원가입이 필요한 사용자입니다."),

    NOT_FOUND(-40400, HttpStatus.NOT_FOUND, "요청과 일치하는 자원이 없습니다."),
    NOT_FOUND_USER(-40401, HttpStatus.NOT_FOUND, "일치하는 사용자가 없습니다."),

    METHOD_NOT_ALLOWED(-40500, HttpStatus.METHOD_NOT_ALLOWED, "수행할 수 없는 요청 경로입니다."),

    INTERNAL_ERROR(-50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    DATA_ACCESS_ERROR(-50001, HttpStatus.INTERNAL_SERVER_ERROR, "데이터 접근 중 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(-50002, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 문제가 발생했습니다."),
    FILE_IO_ERROR(-50003, HttpStatus.INTERNAL_SERVER_ERROR, "IO 과정에서 문제가 발생했습니다."),
    APNS_SIGNING_ERROR(-50004, HttpStatus.INTERNAL_SERVER_ERROR, "APNS 서명 생성 중 오류가 발생했습니다."),
    APNS_AUTHENTICATION_ERROR(-50005, HttpStatus.INTERNAL_SERVER_ERROR, "APNS TLS 자격 증명 중 오류가 발생했습니다."),
    SSL_ERROR(-50005, HttpStatus.INTERNAL_SERVER_ERROR, "SSL 서명 과정에서 오류가 발생했습니다.")
    ;

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
