package site.gonggangam.gonggangam_server.config.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.gonggangam.gonggangam_server.dto.ResponseCode;

@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.VALIDATION_ERROR, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, ResponseCode.INTERNAL_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ResponseCode.valueOf(status), headers, status, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ResponseCode errorCode,
                                                           WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(),
                request);
    }

    private ResponseEntity<Object> handleException(Exception e, ResponseCode code, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                code,
                headers,
                status,
                request
        );
    }

}
