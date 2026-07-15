package com.iiitsonepat.questionbank.service;

import com.iiitsonepat.questionbank.dto.resopnse.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    FileUploadResponse storeFile(
            MultipartFile file,
            Integer academicYear,
            Integer semester
    );

    Resource loadFile(String fileName);

    void deleteFile(String filePath);

    Resource loadFileAsResource(String filePath);

}
