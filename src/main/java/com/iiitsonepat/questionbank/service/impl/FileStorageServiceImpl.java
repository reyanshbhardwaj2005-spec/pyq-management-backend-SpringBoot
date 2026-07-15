package com.iiitsonepat.questionbank.service.impl;

import com.iiitsonepat.questionbank.dto.resopnse.FileUploadResponse;
import com.iiitsonepat.questionbank.exception.FileStorageException;
import com.iiitsonepat.questionbank.exception.ResourceNotFoundException;
import com.iiitsonepat.questionbank.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    @Override
    public void deleteFile(String fileName) {
        try {

            Files.deleteIfExists(Paths.get(fileName));

        } catch (IOException ex) {

            throw new FileStorageException(
                    "Unable to delete file."
            );

        }

    }

    @Override
    public Resource loadFileAsResource(String filePath) {

        try {

            Path path = Paths.get(filePath);

            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return resource;
            }

            throw new ResourceNotFoundException("File not found.");

        } catch (MalformedURLException ex) {

            throw new ResourceNotFoundException("File not found.");

        }

    }


    @Override
    public FileUploadResponse storeFile(MultipartFile file, Integer academicYear, Integer semester) {

        try {

            if (file.isEmpty()) {
                throw new FileStorageException("File is empty.");
            }

            String originalFileName =
                    StringUtils.cleanPath(file.getOriginalFilename());

            if (!originalFileName.toLowerCase().endsWith(".pdf")) {
                throw new FileStorageException("Only PDF files are allowed.");
            }

            String generatedFileName =
                    UUID.randomUUID() + ".pdf";

            Path folder = Paths.get(
                    uploadDirectory,
                    String.valueOf(academicYear),
                    "semester-" + semester
            );

            Files.createDirectories(folder);

            Path targetLocation =
                    folder.resolve(generatedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return FileUploadResponse.builder()
                    .fileName(generatedFileName)
                    .originalFileName(originalFileName)
                    .filePath(targetLocation.toString())
                    .fileSize(file.getSize())
                    .build();

        } catch (IOException ex) {

            throw new FileStorageException(
                    "Could not store file."
            );

        }

    }

    @Override
    public Resource loadFile(String fileName) {
        try {

            Path path = Paths.get(fileName);

            Resource resource =
                    new UrlResource(path.toUri());

            if (resource.exists()) {

                return resource;

            }

            throw new FileStorageException("File not found.");

        } catch (Exception ex) {

            throw new FileStorageException("Unable to load file.");

        }
    }
}
