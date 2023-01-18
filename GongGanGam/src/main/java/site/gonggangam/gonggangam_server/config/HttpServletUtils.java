package site.gonggangam.gonggangam_server.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class HttpServletUtils {

    public static final String HEADER_USER_ID = "userId";


    public static void setUserId(HttpServletRequest request, Long userId) {
        request.setAttribute(HEADER_USER_ID, userId);
    }

    public static Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }
}
