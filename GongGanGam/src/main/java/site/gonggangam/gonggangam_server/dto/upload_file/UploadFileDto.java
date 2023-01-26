package site.gonggangam.gonggangam_server.dto.upload_file;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.config.cloud.MultipartUtil;

@Data
@Builder
public class UploadFileDto {
    private final String id;
    private final String name;
    private final String format;
    private final String path;
    private final Long bytes;
    private String uploadedUrl;

    public static UploadFileDto of(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());

        return UploadFileDto.builder()
                .id(fileId)
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .path(MultipartUtil.createPath(fileId, format))
                .bytes(multipartFile.getSize())
                .build();
    }
}
