package site.gonggangam.gonggangam_server.config.cloud;

import com.amazonaws.services.s3.AmazonS3Client;
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

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void store(String fullPath, MultipartFile multipartFile) throws GeneralException {
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);

        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.FILE_UPLOAD_ERROR);
        } finally {
            if (!file.exists() && !file.delete()) {
                log.warn(String.format("[%s] File %s is not deleted.", getClass(), file));
            }
        }
    }
}
