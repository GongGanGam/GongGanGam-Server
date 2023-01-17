package site.gonggangam.gonggangam_server.dto.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.gonggangam.gonggangam_server.domain.diary.Diary;
import site.gonggangam.gonggangam_server.domain.diary.ShareDiary;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "공유 받은 일기 상세조회")
public class SharedDiaryResponseDto {
    @Schema(description = "일기 ID", defaultValue = "12")
    private final Long diaryId;
    @Schema(description = "날짜", defaultValue = "2023-01-04")
    private final LocalDate date;
    @Schema(description = "이모지", defaultValue = "happy")
    private final String emoji;
    @Schema(description = "내용", defaultValue = "작게 열어둔 문틈 사이로")
    private final String content;
    @Schema(description = "업로드된 이미지", defaultValue = "https://imgurl.com")
    private final String imgUrl;
    @Schema(description = "일기 작성자")
    private final WriterDto writer;

    public static SharedDiaryResponseDto toDto(ShareDiary entity) {
        return SharedDiaryResponseDto.builder()
                .diaryId(entity.getDiary().getDiaryId())
                .date(entity.getDiary().getWritingDate())
                .emoji(entity.getDiary().getEmoji())
                .content(entity.getDiary().getContent())
                .imgUrl(entity.getDiary().getImgUrl())
                .writer(WriterDto.toDto(entity.getReceiver()))
                .build();
    }

    public static SharedDiaryResponseDto toDto(Diary entity) {
        return SharedDiaryResponseDto.builder()
                .diaryId(entity.getDiaryId())
                .date(entity.getWritingDate())
                .emoji(entity.getEmoji())
                .content(entity.getContent())
                .imgUrl(entity.getImgUrl())
                .writer(WriterDto.toDto(entity.getWriter())).
                build();
    }
}
