package site.gonggangam.gonggangam_server.domain.diarys;

import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.users.Users;

import javax.persistence.*;
import java.time.LocalDate;

public class Diary {

    @Id
    @Column(name = "DIARY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID", referencedColumnName = "USER_ID")
    private Users writer;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    private String emoji;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "IMG_URL", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "SHARE_AGREED", nullable = false)
    private Boolean shareAgreed;

    @Convert(converter = ActiveStatus.Converter.class)
    @Column(name = "ACTIVE_STATUS", columnDefinition = "CHAR(1)", length = 1, nullable = false)
    private ActiveStatus activeStatus;

}
