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
        private final String nickname;
        private final Integer birthYear;
        private final String shareType;
        private final String gender;
    }

    @Data
    @Builder
    public static class PatchProfImg {
        private final String imgUrl;
    }

}
