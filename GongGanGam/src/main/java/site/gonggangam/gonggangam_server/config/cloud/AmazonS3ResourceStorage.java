package site.gonggangam.gonggangam_server.config.cloud;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;

import java.io.File;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmazonS3ResourceStorage {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.base-url}")
    private String baseUrl;

    public String store(String fullPath, MultipartFile multipartFile) throws GeneralException {
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);

        if (!file.exists() && !file.mkdirs()) {
            // 경로가 존재하지 않고, 폴도 생성에 실패한 경우
            throw new GeneralException(ResponseCode.FILE_IO_ERROR);
        }

        try {
            multipartFile.transferTo(file);
            amazonS3.putObject(new PutObjectRequest(bucket, fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return baseUrl + fullPath;

        } catch (Exception e) {
            log.error(String.format("[%s] File upload failed. nested exception is %s", getClass(), e));
            throw new GeneralException(ResponseCode.FILE_UPLOAD_ERROR);
        } finally {
            if (!file.exists() && !file.delete()) {
                log.warn(String.format("[%s] File %s is not deleted.", getClass(), file));
            }
        }
    }
}
