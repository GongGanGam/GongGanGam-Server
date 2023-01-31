package site.gonggangam.gonggangam_server.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static site.gonggangam.gonggangam_server.config.ResponseCode.Constants.*;

@Getter
public enum ResponseCode {
    OK(20000, HttpStatus.OK, "요청이 완료되었습니다.", OK_VALUE),
    CREATED(20100, HttpStatus.CREATED, "요청이 완료되었습니다.", CREATED_VALUE),

    BAD_REQUEST(-40000, HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다.", BAD_REQUEST_VALUE),
    VALIDATION_ERROR(-40001, HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다.", VALIDATION_ERROR_VALUE),
    REQUIRE_VALUE(-40002, HttpStatus.BAD_REQUEST, "누락된 값이 있습니다.", REQUIRE_VALUE_VALUE),
    WRONG_OAUTH_TOKEN(-40003, HttpStatus.BAD_REQUEST, "소셜로그인에 필요한 토큰이 잘못되었습니다.", WRONG_OAUTH_TOKEN_VALUE),
    REQUIRE_OAUTH_EMAIL(-40004, HttpStatus.BAD_REQUEST, "소셜로그인의 이메일 제공 동의가 필요합니다.", REQUIRE_OAUTH_EMAIL_VALUE),

    TOKEN_INVALID(-40100, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", TOKEN_INVALID_VALUE),
    TOKEN_EXPIRED(-40101, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.", TOKEN_EXPIRED_VALUE),
    TOKEN_IS_NULL(-40102, HttpStatus.UNAUTHORIZED, "토큰이 없습니다.", TOKEN_IS_NULL_VALUE),
    TOKEN_CAN_NOT_DECODE(-40103, HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식입니다.", TOKEN_CAN_NOT_DECODE_VALUE),
    AUTHENTICATION_INVALID_USER(-40104, HttpStatus.UNAUTHORIZED, "이용할 수 없는 사용자입니다.", AUTHENTICATION_INVALID_USER_VALUE),

    PERMISSION_DENIED(-40300, HttpStatus.FORBIDDEN, "접근 권한이 없습니다.", PERMISSION_DENIED_VALUE),
    REQUIRED_SIGNUP(-40301, HttpStatus.FORBIDDEN, "추가 회원가입이 필요한 사용자입니다.", REQUIRED_SIGNUP_VALUE),

    NOT_FOUND(-40400, HttpStatus.NOT_FOUND, "요청과 일치하는 자원이 없습니다.", NOT_FOUND_VALUE),
    NOT_FOUND_USER(-40401, HttpStatus.NOT_FOUND, "일치하는 사용자가 없습니다.", NOT_FOUND_USER_VALUE),

    METHOD_NOT_ALLOWED(-40500, HttpStatus.METHOD_NOT_ALLOWED, "수행할 수 없는 요청 경로입니다.", METHOD_NOT_ALLOWED_VALUE),

    DIARY_DATE_INVALID(-40901, HttpStatus.CONFLICT, "아직 일기를 작성할 수 없는 날짜입니다.", DIARY_DATE_INVALID_VALUE),
    DIARY_DATE_CONFLICT(-40902, HttpStatus.CONFLICT, "이미 일기를 작성한 날짜입니다.", DIARY_DATE_CONFLICT_VALUE),

    INTERNAL_ERROR(-50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.", INTERNAL_ERROR_VALUE),
    DATA_ACCESS_ERROR(-50001, HttpStatus.INTERNAL_SERVER_ERROR, "데이터 접근 중 오류가 발생했습니다.", DATA_ACCESS_ERROR_VALUE),
    FILE_UPLOAD_ERROR(-50002, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 문제가 발생했습니다.", FILE_UPLOAD_ERROR_VALUE),
    FILE_IO_ERROR(-50003, HttpStatus.INTERNAL_SERVER_ERROR, "IO 과정에서 문제가 발생했습니다.", FILE_IO_ERROR_VALUE),
    APNS_SIGNING_ERROR(-50004, HttpStatus.INTERNAL_SERVER_ERROR, "APNS 서명 생성 중 오류가 발생했습니다.", APNS_SIGNING_ERROR_VALUE),
    APNS_AUTHENTICATION_ERROR(-50005, HttpStatus.INTERNAL_SERVER_ERROR, "APNS TLS 자격 증명 중 오류가 발생했습니다.", APNS_AUTHENTICATION_ERROR_VALUE),
    SSL_ERROR(-50005, HttpStatus.INTERNAL_SERVER_ERROR, "SSL 서명 과정에서 오류가 발생했습니다.", SSL_ERROR_VALUE)
    ;

    ResponseCode(Integer code, HttpStatus httpStatus, String message, String codeName) {
        if (!codeName.equals(this.name()))
            throw new IllegalArgumentException(String.format("[%s] Wrong response code name.", getClass()));

        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.codeName = codeName;
    }

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
    private final String codeName;


    public static class Constants {
        public static final String OK_VALUE = "OK";
        public static final String CREATED_VALUE = "CREATED";
        public static final String BAD_REQUEST_VALUE = "BAD_REQUEST";
        public static final String VALIDATION_ERROR_VALUE = "VALIDATION_ERROR";
        public static final String REQUIRE_VALUE_VALUE = "REQUIRE_VALUE";
        public static final String WRONG_OAUTH_TOKEN_VALUE = "WRONG_OAUTH_TOKEN";
        public static final String REQUIRE_OAUTH_EMAIL_VALUE = "REQUIRE_OAUTH_EMAIL";
        public static final String TOKEN_INVALID_VALUE = "TOKEN_INVALID";
        public static final String TOKEN_EXPIRED_VALUE = "TOKEN_EXPIRED";
        public static final String TOKEN_IS_NULL_VALUE = "TOKEN_IS_NULL";
        public static final String TOKEN_CAN_NOT_DECODE_VALUE = "TOKEN_CAN_NOT_DECODE";
        public static final String AUTHENTICATION_INVALID_USER_VALUE = "AUTHENTICATION_INVALID_USER";
        public static final String PERMISSION_DENIED_VALUE = "PERMISSION_DENIED";
        public static final String REQUIRED_SIGNUP_VALUE = "REQUIRED_SIGNUP";
        public static final String NOT_FOUND_VALUE = "NOT_FOUND";
        public static final String NOT_FOUND_USER_VALUE = "NOT_FOUND_USER";
        public static final String METHOD_NOT_ALLOWED_VALUE = "METHOD_NOT_ALLOWED";
        public static final String DIARY_DATE_INVALID_VALUE = "DIARY_DATE_INVALID";
        public static final String DIARY_DATE_CONFLICT_VALUE = "DIARY_DATE_CONFLICT";
        public static final String INTERNAL_ERROR_VALUE = "INTERNAL_ERROR";
        public static final String DATA_ACCESS_ERROR_VALUE = "DATA_ACCESS_ERROR";
        public static final String FILE_UPLOAD_ERROR_VALUE = "FILE_UPLOAD_ERROR";
        public static final String FILE_IO_ERROR_VALUE = "FILE_IO_ERROR";
        public static final String APNS_SIGNING_ERROR_VALUE = "APNS_SIGNING_ERROR";
        public static final String APNS_AUTHENTICATION_ERROR_VALUE = "APNS_AUTHENTICATION_ERROR";
        public static final String SSL_ERROR_VALUE = "SSL_ERROR";
    }
}
