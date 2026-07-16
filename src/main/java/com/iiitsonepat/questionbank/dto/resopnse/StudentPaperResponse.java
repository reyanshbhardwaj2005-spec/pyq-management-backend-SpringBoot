package com.iiitsonepat.questionbank.dto.resopnse;

import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class StudentPaperResponse {

    private Long id;

    private String title;

    private Integer semester;

    private ExaminationType examinationType;

    private Integer academicYear;

    private Branch branch;

    private LocalDateTime uploadedAt;
}
