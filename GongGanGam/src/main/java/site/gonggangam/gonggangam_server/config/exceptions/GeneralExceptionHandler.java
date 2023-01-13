package site.gonggangam.gonggangam_server.config.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.gonggangam.gonggangam_server.dto.ResponseCode;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice(annotations = {RestController.class})
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.VALIDATION_ERROR, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleGeneralException(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    // TODO : 유효하지 않은 토큰과 토큰 만료 구분
    @ExceptionHandler(value = {AuthenticationServiceException.class })
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationServiceException e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.TOKEN_INVALID, request);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.PERMISSION_DENIED, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.INTERNAL_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.valueOf(status), headers, status, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ResponseCode errorCode,
                                                           WebRequest request) {
        return handleExceptionInternal(
                e,
                errorCode,
                HttpHeaders.EMPTY,
                errorCode.getHttpStatus(),
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ResponseCode code, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                code,
                headers,
                status,
                request
        );
    }

}
