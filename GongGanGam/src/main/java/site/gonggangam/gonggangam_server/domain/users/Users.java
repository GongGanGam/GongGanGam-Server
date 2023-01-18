package site.gonggangam.gonggangam_server.domain.users;

import com.nimbusds.openid.connect.sdk.claims.Gender;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.types.ProviderType;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;
import site.gonggangam.gonggangam_server.domain.users.types.Role;
import site.gonggangam.gonggangam_server.domain.users.types.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@Entity
public class Users extends BaseTimeEntity implements UserDetails {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "IDENTIFICATION", columnDefinition = "VARCHAR(15)", unique = true, nullable = false)
    private String identification;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String profImg;

    @Column(name = "EMAIL", columnDefinition = "VARCHAR(50)", length = 50)
    private String email;

    @Convert(converter = ProviderType.Converter.class)
    @Column(name = "PROVIDER", columnDefinition = "CHAR(2)", length = 2, nullable = false)
    private ProviderType provider;

    @Column(name = "DEVICE_TOKEN", columnDefinition = "TEXT", nullable = true)
    private String deviceToken;

    @Convert(converter = Role.Converter.class)
    @Column(name = "ROLE", columnDefinition = "CHAR(1) DEFAULT 'U'", length = 1, nullable = false)
    private Role role;

    @Convert(converter = UserStatus.Converter.class)
    @Column(name = "USER_STATUS", columnDefinition = "CHAR(1) DEFAULT 'N'", length = 1, nullable = false)
    private UserStatus userStatus;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private UserSettings settings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn
    private UserInfo userInfo;

    @Builder
    public Users(String identification, String profImg, String email, ProviderType provider, String deviceToken, Role role, UserStatus userStatus, UserSettings settings, UserInfo userInfo) {
        this.identification = identification;
        this.profImg = profImg;
        this.email = email;
        this.provider = provider;
        this.deviceToken = deviceToken;
        this.role = role;
        this.userStatus = userStatus;
        this.settings = settings;
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> this.role.getKey());
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.identification;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userStatus != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userStatus == UserStatus.NORMAL;
    }
}
