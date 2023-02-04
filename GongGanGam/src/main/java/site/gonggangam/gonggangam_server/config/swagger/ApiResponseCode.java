package site.gonggangam.gonggangam_server.config.swagger;

import site.gonggangam.gonggangam_server.config.ResponseCode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ANNOTATION_TYPE, METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseCode {
    ResponseCode value();
}
