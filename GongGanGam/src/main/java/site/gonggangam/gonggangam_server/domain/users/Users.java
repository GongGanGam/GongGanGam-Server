package site.gonggangam.gonggangam_server.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.user_settings.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;

import javax.persistence.*;

@Getter
@Builder
@RequiredArgsConstructor
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

    @Convert(converter = ActiveStatus.Converter.class)
    @Column(name = "ACTIVE_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ActiveStatus activeStatus;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserSettings settings;

    public void update(String nickname, int birthYear, GenderType genderType) {
        this.nickname = nickname;
        this.birthYear = birthYear;
        this.genderType = genderType;
    }

}
