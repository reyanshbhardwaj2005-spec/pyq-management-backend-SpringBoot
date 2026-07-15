package com.iiitsonepat.questionbank.dto.resopnse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {

    private String fileName;

    private String originalFileName;

    private String filePath;

    private Long fileSize;
}
