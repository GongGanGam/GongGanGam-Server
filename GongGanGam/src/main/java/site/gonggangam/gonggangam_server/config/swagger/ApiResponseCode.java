package site.gonggangam.gonggangam_server.config.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import site.gonggangam.gonggangam_server.config.ResponseCode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Target({ANNOTATION_TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseCode {
    ResponseCode value();
    Schema[] schema() default {};
}
