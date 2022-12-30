package site.gonggangam.gonggangam_server.dto.users;

import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.users.types.Gender;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;

@Data
public class UsersRequestDto {

    @Data
    @Builder
    public static class Post {

    }

    @Data
    @Builder
    public static class Patch {
        private final String nickname;
        private final int birthYear;
        private final ShareType shareType;
        private final Gender gender;
    }

}
