package site.gonggangam.gonggangam_server.domain.users;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_INFO")
@Entity
public class UserInfo extends BaseTimeEntity implements Serializable {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @Column(columnDefinition = "VARCHAR(45)", length = 45, nullable = true)
    private String nickname;

    @Column(name = "BIRTH_YEAR", columnDefinition = "INT(4)", nullable = true)
    private Integer birthYear;

    @Convert(converter = GenderType.Converter.class)
    @Column(columnDefinition = "CHAR(1)", length = 1, nullable = true)
    private GenderType genderType;

    @Builder
    public UserInfo(Users user, String nickname, Integer birthYear, GenderType genderType) {
        this.user = user;
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.genderType = genderType;
    }

    public void update(String nickname, Integer birthYear, GenderType genderType) {
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.genderType = genderType;
    }
}
