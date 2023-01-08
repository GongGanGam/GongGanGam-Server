package site.gonggangam.gonggangam_server.domain.users;

import lombok.*;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;
import site.gonggangam.gonggangam_server.domain.users.types.Role;
import site.gonggangam.gonggangam_server.domain.users.types.UserStatus;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(columnDefinition = "VARCHAR(45)", length = 45, nullable = false)
    private String nickname;

    @Column(name = "BIRTH_YEAR", nullable = false)
    private Integer birthYear;

    @Convert(converter = GenderType.Converter.class)
    @Column(columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private GenderType genderType;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String profImg;

    @Column(columnDefinition = "VARCHAR(50)", length = 50, nullable = false)
    private String email;

    @Convert(converter = AuthType.Converter.class)
    @Column(name = "AUTH_TYPE", columnDefinition = "CHAR(2)", length = 2, nullable = false)
    private AuthType authType;

    @Column(name = "DEVICE_TOKEN", columnDefinition = "TEXT", nullable = true)
    private String deviceToken;

    @Convert(converter = Role.Converter.class)
    @Column(name = "ROLE", columnDefinition = "CHAR(1) DEFAULT 'U'", length = 1, nullable = false)
    private Role role;

    @Convert(converter = UserStatus.Converter.class)
    @Column(name = "USER_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private UserStatus userStatus;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToOne(mappedBy = "user")
    private UserSettings settings;

    @Builder
    public Users(String nickname, Integer birthYear, GenderType genderType, String profImg, String email, AuthType authType, String deviceToken, Role role, UserStatus userStatus, UserSettings settings) {
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.genderType = genderType;
        this.profImg = profImg;
        this.email = email;
        this.authType = authType;
        this.deviceToken = deviceToken;
        this.role = role;
        this.userStatus = userStatus;
        this.settings = settings;
    }

    public void update(String nickname, int birthYear, GenderType genderType) {
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.genderType = genderType;
    }

}
