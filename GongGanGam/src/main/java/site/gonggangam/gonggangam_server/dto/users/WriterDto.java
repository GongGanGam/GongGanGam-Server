package site.gonggangam.gonggangam_server.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.users.Users;

@Data
@Builder
@Schema(description = "일기 작성자의 공개된 정보")
public class WriterDto {
    @Schema(description = "사용자 ID", defaultValue = "123")
    private final Long userId;
    @Schema(description = "닉네임", defaultValue = "오늘도맑음")
    private final String nickname;
    @Schema(description = "프로필 이미지", defaultValue = "https://imgurl.com")
    private final String profImg;

    public static WriterDto toDto(Users entity) {
        return WriterDto.builder()
                .userId(entity.getUserId())
                .nickname(entity.getUserInfo().getNickname())
                .profImg(entity.getProfImg())
                .build();
    }
}
