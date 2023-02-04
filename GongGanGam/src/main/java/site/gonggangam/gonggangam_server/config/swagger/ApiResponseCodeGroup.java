package site.gonggangam.gonggangam_server.config.swagger;

import static site.gonggangam.gonggangam_server.config.ResponseCode.*;

public enum ApiResponseCodeGroup {
    @ApiResponseCodes(value = {
            @ApiResponseCode(TOKEN_INVALID),
            @ApiResponseCode(TOKEN_EXPIRED),
            @ApiResponseCode(TOKEN_IS_NULL),
            @ApiResponseCode(TOKEN_CAN_NOT_DECODE),
            @ApiResponseCode(AUTHENTICATION_INVALID_USER),
            @ApiResponseCode(REQUIRED_SIGNUP)
    })
    AUTHENTICATED,

    @ApiResponseCodes(value = {
            @ApiResponseCode(PERMISSION_DENIED)
    })
    PERMITTED,

    @ApiResponseCodes(value = {
            @ApiResponseCode(NOT_FOUND)
    })
    OPTIONAL,

    @ApiResponseCodes(value = {
            @ApiResponseCode(WRONG_OAUTH_TOKEN),
            @ApiResponseCode(REQUIRE_OAUTH_EMAIL)
    })
    OAUTH_SIGN_IN
}
