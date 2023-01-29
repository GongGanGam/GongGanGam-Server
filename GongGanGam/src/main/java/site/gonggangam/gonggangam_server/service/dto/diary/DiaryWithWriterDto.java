package site.gonggangam.gonggangam_server.service.dto.diary;

import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.Users;

@Data
@Builder
public final class DiaryWithWriterDto {
    private final Diary diary;
    private final Users writer;
    private final UserSettings writerSettings;
}