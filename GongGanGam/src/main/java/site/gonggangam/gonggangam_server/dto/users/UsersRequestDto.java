package site.gonggangam.gonggangam_server.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

import javax.validation.constraints.NotNull;

public class UsersRequestDto {

    @Data
    @Builder
    @Schema(description = "사용자 추가정보")
    public static class PostUser {
        @Schema(description = "닉네임", defaultValue = "오늘도맑음")
        @NotNull
        private final String nickname;
        @Schema(description = "출생년도", defaultValue = "1997")
        @NotNull
        private final String birthYear;
        @Schema(description = "성별 (unknown : 미선택, male : 남성, female : 여성)", defaultValue = "unknown", allowableValues = {"unknown", "male", "female"})
        private final GenderType gender;
    }

    @Data
    @Builder
    @Schema(description = "사용자 정보 수정")
    public static class PutUserInfo {
        @Schema(description = "닉네임", defaultValue = "오늘도맑음")
        private final String nickname;
        @Schema(description = "출생년도", defaultValue = "1997")
        private final String birthYear;
        @Schema(description = "일기 공유 연령대 타입 (all : 선택하지 않음, similar : 비슷한 연령대)", defaultValue = "all", allowableValues = {"all", "similar"})
        private final ShareType shareType;
        @Schema(description = "성별 (unknown : 미선택, male : 남성, female : 여성)", defaultValue = "unknown", allowableValues = {"unknown", "male", "female"})
        private final GenderType gender;
    }

}
