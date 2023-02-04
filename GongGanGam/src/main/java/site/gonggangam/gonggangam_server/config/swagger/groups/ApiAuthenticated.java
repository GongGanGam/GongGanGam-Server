package site.gonggangam.gonggangam_server.config.swagger.groups;

import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCode;
import site.gonggangam.gonggangam_server.config.swagger.ApiResponseCodeGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static site.gonggangam.gonggangam_server.config.ResponseCode.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponseCodeGroup(
        value = {
                @ApiResponseCode(TOKEN_INVALID),
                @ApiResponseCode(TOKEN_EXPIRED),
                @ApiResponseCode(TOKEN_IS_NULL),
                @ApiResponseCode(TOKEN_CAN_NOT_DECODE),
                @ApiResponseCode(AUTHENTICATION_INVALID_USER),
        }
)
public @interface ApiAuthenticated {
}
