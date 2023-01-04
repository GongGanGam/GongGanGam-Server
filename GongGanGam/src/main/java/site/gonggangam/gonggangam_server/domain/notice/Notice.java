package site.gonggangam.gonggangam_server.domain.notice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "NOTICE")
@Entity
public class Notice extends BaseTimeEntity {

    @Id
    @Column(name = "NOTICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Convert(converter = ActiveStatus.Converter.class)
    @Column(name = "ACTIVE_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ActiveStatus activeStatus;

    @Builder
    public Notice(String title, String content, ActiveStatus activeStatus) {
        this.title = title;
        this.content = content;
        this.activeStatus = activeStatus;
    }
}
