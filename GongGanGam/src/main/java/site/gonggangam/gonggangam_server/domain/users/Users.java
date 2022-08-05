package site.gonggangam.gonggangam_server.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIdx;

    @Column(length = 45, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int birthYear;

    @Column(nullable = false)
    private char gender;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String profImg;

    @Column(length = 45, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String identification;

    @Column(nullable = false)
    private char setAge;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String deviceToken;

    @Column(length = 45, nullable = false)
    private String status;

    @Builder
    public Users(String nickname, int birthYear, char gender, String profImg, String email, String type, String identification, char setAge, String deviceToken, String status) {
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.gender = gender;
        this.profImg = profImg;
        this.email = email;
        this.type = type;
        this.identification = identification;
        this.setAge = setAge;
        this.deviceToken = deviceToken;
        this.status = status;
    }

    public void update(String nickname, int birthYear, char setAge, char gender) {
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.setAge = setAge;
        this.gender = gender;
    }

}
