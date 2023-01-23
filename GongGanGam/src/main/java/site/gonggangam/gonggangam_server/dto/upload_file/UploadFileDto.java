package site.gonggangam.gonggangam_server.dto.upload_file;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class UploadFileDto {
    private final String id;
    private final String name;
    private final String format;
    private final String path;
    private final Long bytes;
    private final LocalDateTime createdAt;

    public static UploadFileDto of(MultipartFile multipartFile) {
        final String fileId = Multipart
    }
}
