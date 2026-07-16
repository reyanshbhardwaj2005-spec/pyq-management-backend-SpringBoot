package com.iiitsonepat.questionbank.dto.request;

import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePaperRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 1)
    @Max(value = 8)
    private Integer semester;

    @NotNull(message = "Examination Type is required")
    private ExaminationType examinationType;

    @NotNull(message = "Academic Year is required")
    private Integer academicYear;

    @NotNull(message = "Branch is required")
    private Branch branch;

}