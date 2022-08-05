package site.gonggangam.gonggangam_server.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchUsersRequestDto {

    private final String nickname;
    private int birthYear;
    private char setAge;
    private char gender;

}
