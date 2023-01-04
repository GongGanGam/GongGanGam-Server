package site.gonggangam.gonggangam_server.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class UsersRequestDto {

    @Data
    @Builder
    @Schema(description = "사용자 추가정보")
    public static class Post {
        @Schema(description = "닉네임", defaultValue = "오늘도맑음")
        private final String nickname;
        @Schema(description = "출생년도", defaultValue = "1997")
        private final String birthYear;
        @Schema(description = "성별 (undefine : 미선택, male : 남성, female : 여성)", defaultValue = "undefine", allowableValues = {"undefine", "male", "female"})
        private final String gender;
    }

    @Data
    @Builder
    public static class PutInfo {
        @Schema(description = "닉네임", defaultValue = "오늘도맑음")
        private final String nickname;
        @Schema(description = "출생년도", defaultValue = "1997")
        private final String birthYear;
        @Schema(description = "일기 공유 연령대 타입 (all : 선택하지 않음, similar : 비슷한 연령대)", defaultValue = "all", allowableValues = {"all", "similar"})
        private final String shareType;
        @Schema(description = "성별 (undefine : 미선택, male : 남성, female : 여성)", defaultValue = "undefine", allowableValues = {"undefine", "male", "female"})
        private final String gender;
    }

}
