package site.gonggangam.gonggangam_server.config.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.dto.ErrorResponseDto;


@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.VALIDATION_ERROR, request);
    }

    @ExceptionHandler(value = {GeneralException.class})
    public ResponseEntity<Object> handleGeneralException(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.NOT_FOUND, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        log.error("server internal error occurred: " + e + " request = " + request);
        return handleExceptionInternal(e, ResponseCode.INTERNAL_ERROR, request);
    }

//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body,
//                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return handleExceptionInternal(e, ResponseCode.valueOf(status), headers, status, request);
//    }

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

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ErrorResponseDto.of(code));

    }
}