package com.iiitsonepat.questionbank.dto.resopnse;

import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.enums.PaperStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class PaperResponse {
    private Long id;

    private String title;

    private Integer semester;

    private ExaminationType examinationType;

    private Integer academicYear;

    private String batch;

    private String originalFileName;

    private Long fileSize;

    private PaperStatus status;

    private LocalDateTime uploadedAt;
}
