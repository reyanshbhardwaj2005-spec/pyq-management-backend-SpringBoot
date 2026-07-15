package com.iiitsonepat.questionbank.mapper;

import com.iiitsonepat.questionbank.dto.resopnse.PaperResponse;
import com.iiitsonepat.questionbank.entity.QuestionPaper;

public class QuestionPaperMapper {

    private QuestionPaperMapper() {
    }

    public static PaperResponse toResponse(QuestionPaper paper) {

        return PaperResponse.builder()
                .id(paper.getId())
                .title(paper.getTitle())
                .semester(paper.getSemester())
                .academicYear(paper.getAcademicYear())
                .batch(paper.getBatch())
                .examinationType(paper.getExaminationType())
                .originalFileName(paper.getOriginalFileName())
                .fileSize(paper.getFileSize())
                .status(paper.getStatus())
                .uploadedAt(paper.getUploadedAt())
                .build();

    }

}
