package com.iiitsonepat.questionbank.dto.request;

import com.iiitsonepat.questionbank.enums.ExaminationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadPaperRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Semester is required")
    @Min(value = 1)
    @Max(value = 8)
    private Integer semester;

    @NotNull(message = "Examination type is required")
    private ExaminationType examinationType;

    @NotNull(message = "Academic year is required")
    private Integer academicYear;

    @NotBlank(message = "Batch is required")
    private String batch;
}
