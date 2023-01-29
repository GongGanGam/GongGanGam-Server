package site.gonggangam.gonggangam_server.service;

import org.springframework.web.multipart.MultipartFile;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.service.dto.upload_file.UploadFileDto;

public interface UploadFileService {

    UploadFileDto save(MultipartFile multipartFile) throws GeneralException;

    // TODO : delete file
}
