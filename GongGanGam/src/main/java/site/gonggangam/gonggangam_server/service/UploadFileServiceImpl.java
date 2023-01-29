package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.config.cloud.AmazonS3ResourceStorage;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.controller.dto.upload_file.UploadFileDto;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Override
    public UploadFileDto save(MultipartFile multipartFile) throws GeneralException {
        UploadFileDto uploadFileDto = UploadFileDto.of(multipartFile);
        String url = amazonS3ResourceStorage.store(uploadFileDto.getPath(), multipartFile);
        uploadFileDto.setUploadedUrl(url);
        return uploadFileDto;
    }
}
