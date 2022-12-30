package site.gonggangam.gonggangam_server.dto.users;

import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;

public class UsersRequestDto {

    @Data
    @Builder
    public static class Post {
        private final String nickname;
        private final Integer birthYear;
        private final GenderType gender;
    }

    @Data
    @Builder
    public static class PatchInfo {
        private String nickname;
        private Integer birthYear;
        private String shareType;
        private String gender;
    }

    @Data
    @Builder
    public static class PatchProfImg {
        private String imgUrl;
    }

}
