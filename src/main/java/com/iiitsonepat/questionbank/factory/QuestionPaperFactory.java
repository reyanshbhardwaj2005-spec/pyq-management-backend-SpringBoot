package com.iiitsonepat.questionbank.factory;
import com.iiitsonepat.questionbank.dto.request.UpdatePaperRequest;
import com.iiitsonepat.questionbank.dto.request.UploadPaperRequest;
import com.iiitsonepat.questionbank.dto.resopnse.FileUploadResponse;
import com.iiitsonepat.questionbank.entity.QuestionPaper;
import org.springframework.stereotype.Component;

@Component
public class QuestionPaperFactory {


    public QuestionPaper create(
            UploadPaperRequest request,
            FileUploadResponse uploadedFile) {

        return QuestionPaper.builder()

                .title(request.getTitle())

                .semester(request.getSemester())

                .academicYear(request.getAcademicYear())

                .branch(request.getBranch())

                .examinationType(request.getExaminationType())

                .fileName(uploadedFile.getFileName())

                .originalFileName(uploadedFile.getOriginalFileName())

                .filePath(uploadedFile.getFilePath())

                .fileSize(uploadedFile.getFileSize())

                .build();

    }

    public void updateEntity(
            QuestionPaper paper,
            UpdatePaperRequest request
    ) {

        paper.setTitle(request.getTitle());
        paper.setSemester(request.getSemester());
        paper.setAcademicYear(request.getAcademicYear());
        paper.setBranch(request.getBranch());
        paper.setExaminationType(request.getExaminationType());

    }
}
