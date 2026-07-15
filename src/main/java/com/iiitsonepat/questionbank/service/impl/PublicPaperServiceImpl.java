package com.iiitsonepat.questionbank.service.impl;

import com.iiitsonepat.questionbank.dto.resopnse.StudentPaperResponse;
import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.enums.PaperStatus;
import com.iiitsonepat.questionbank.provider.QuestionPaperProvider;
import com.iiitsonepat.questionbank.repository.QuestionPaperRepository;
import com.iiitsonepat.questionbank.service.FileStorageService;
import com.iiitsonepat.questionbank.service.PublicPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PublicPaperServiceImpl implements PublicPaperService {

    private final QuestionPaperProvider questionPaperProvider;
    private final FileStorageService fileStorageService;
    private final QuestionPaperRepository questionPaperRepository;

    @Override
    public StudentPaperResponse searchPaper(Integer semester, ExaminationType examinationType, Integer academicYear, String batch) {
        QuestionPaper paper = questionPaperProvider.getActivePaper(
                semester,
                examinationType,
                academicYear,
                batch);
        return StudentPaperResponse.builder()
                .id(paper.getId())
                .title(paper.getTitle())
                .semester(paper.getSemester())
                .academicYear(paper.getAcademicYear())
                .batch(paper.getBatch())
                .examinationType(paper.getExaminationType())
                .uploadedAt(paper.getUploadedAt())
                .build();
    }

    @Override
    public Resource downloadPaper(Long id) {
        QuestionPaper paper = questionPaperProvider.getActivePaper(id);
        return fileStorageService.loadFileAsResource(paper.getFilePath());
    }

    @Override
    public List<Integer> getAcademicYears() {
        return questionPaperRepository.findDistinctAcademicYears(PaperStatus.ACTIVE);
    }

    @Override
    public List<String> getBatches() {
        return questionPaperRepository.findDistinctBatches(PaperStatus.ACTIVE);
    }

    @Override
    public List<ExaminationType> getExaminationTypes() {
        return Arrays.asList(ExaminationType.values());
    }

    @Override
    public List<Integer> getSemesters() {
        return IntStream.rangeClosed(1,8).boxed().toList();
    }
}
