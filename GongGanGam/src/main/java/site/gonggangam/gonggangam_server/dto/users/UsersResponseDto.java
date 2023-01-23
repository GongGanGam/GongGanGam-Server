package site.gonggangam.gonggangam_server.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.Users;

@Data
@Builder
@Schema(description = "사용자 정보")
public class UsersResponseDto {

    @Schema(description = "닉네임", defaultValue = "오늘도맑음")
    private final String nickname;
    @Schema(description = "출생년도", defaultValue = "1997")
    private final String birthYear;
    @Schema(description = "이메일", defaultValue = "chicken@gmail.com")
    private final String email;
    @Schema(description = "프로필 이미지 url", defaultValue = "https://image_url")
    private final String profImg;
    @Schema(description = "일기 공유 연령대 타입 (all : 선택하지 않음, similar : 비슷한 연령대)", defaultValue = "all", allowableValues = {"all", "similar"})
    private final String shareType;
    @Schema(description = "성별 (undefine : 미선택, male : 남성, female : 여성)", defaultValue = "undefine", allowableValues = {"undefine", "male", "female"})
    private final String gender;

    public static UsersResponseDto of(Users user, UserSettings settings) {
        return UsersResponseDto.builder()
                .nickname(user.getUserInfo().getNickname())
                .birthYear(user.getUserInfo().getBirthYear().toString())
                .email(user.getEmail())
                .gender(user.getUserInfo().getGenderType().getTitle())
                .profImg(user.getProfImg())
                .shareType(settings.getShareType().getTitle())
                .build();
    }
}
