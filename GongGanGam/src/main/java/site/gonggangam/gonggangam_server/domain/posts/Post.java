package site.gonggangam.gonggangam_server.domain.posts;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POST_TYPE")
public class Post extends BaseTimeEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", referencedColumnName = "USER_ID")
    protected Users writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Convert(converter = ActiveStatus.Converter.class)
    @Column(name = "ACTIVE_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    protected ActiveStatus activeStatus;

    @Builder
    public Post(Users writer, String content, ActiveStatus activeStatus) {
        this.writer = writer;
        this.content = content;
        this.activeStatus = activeStatus;
    }
}
