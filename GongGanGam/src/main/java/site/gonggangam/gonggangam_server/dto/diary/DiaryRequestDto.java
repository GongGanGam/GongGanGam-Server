package site.gonggangam.gonggangam_server.dto.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

// TODO : abstract로 바꾸기
public class DiaryRequestDto {

    @Data
    @Builder
    @Schema(description = "일기 작성")
    public static class Post implements Serializable {
        @Schema(description = "작성 일자 (yyyy-MM-dd)", defaultValue = "2022-01-01", requiredMode = REQUIRED)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private final LocalDate date;
        @Schema(description = "이모지", defaultValue = "happy", requiredMode = REQUIRED)
        private final String emoji;
        @Schema(description = "내용", defaultValue = "내 손 잡아준 너 매일 아침 눈을 뜰 때면", requiredMode = REQUIRED)
        private final String content;
        @Schema(description = "공유 여부", defaultValue = "true", requiredMode = REQUIRED)
        private final Boolean shareAgreed;
        @Schema(description = "이미지 파일", requiredMode = NOT_REQUIRED)
        private final MultipartFile imgFile;
    }

    @Data
    @Builder
    @Schema(description = "일기 수정")
    public static class Put implements Serializable {
        @Schema(description = "이모지", defaultValue = "happy", requiredMode = REQUIRED)
        private final String emoji;
        @Schema(description = "내용", defaultValue = "내 손 잡아준 너 매일 아침 눈을 뜰 때면", requiredMode = REQUIRED)
        private final String content;
        @Schema(description = "공유 여부", defaultValue = "true", requiredMode = REQUIRED)
        private final Boolean shareAgreed;
        @Schema(description = "이미지 파일", requiredMode = NOT_REQUIRED)
        private final MultipartFile imgFile;
    }
}
