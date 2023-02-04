package site.gonggangam.gonggangam_server.config.swagger;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiResponseCodes {
    ApiResponseCode[] value() default {};
    ApiResponseCodeGroup[] groups() default {};
}
