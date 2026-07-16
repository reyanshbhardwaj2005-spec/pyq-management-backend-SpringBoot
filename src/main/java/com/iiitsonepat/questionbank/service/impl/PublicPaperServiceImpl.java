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
import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.specification.QuestionPaperSpecification;

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
    public List<StudentPaperResponse> searchPapers(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            Branch branch
    ) {

        List<QuestionPaper> papers = questionPaperRepository.findAll(
                QuestionPaperSpecification.search(
                        semester,
                        examinationType,
                        academicYear,
                        branch
                )
        );

        System.out.println("==================================");
        System.out.println("Semester = " + semester);
        System.out.println("Exam = " + examinationType);
        System.out.println("Year = " + academicYear);
        System.out.println("Branch = " + branch);
        System.out.println("Papers Found = " + papers.size());

        for (QuestionPaper p : papers) {
            System.out.println(
                    "ID=" + p.getId()
                            + ", Title=" + p.getTitle()
                            + ", Branch=" + p.getBranch()
                            + ", Year=" + p.getAcademicYear()
                            + ", Semester=" + p.getSemester()
                            + ", Exam=" + p.getExaminationType()
            );
        }
        System.out.println("==================================");

        return papers.stream()
                .map(paper -> StudentPaperResponse.builder()
                        .id(paper.getId())
                        .title(paper.getTitle())
                        .semester(paper.getSemester())
                        .academicYear(paper.getAcademicYear())
                        .branch(paper.getBranch())
                        .examinationType(paper.getExaminationType())
                        .uploadedAt(paper.getUploadedAt())
                        .build())
                .toList();
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
    public List<Branch> getBranches() {
        return questionPaperRepository.findDistinctBranches(PaperStatus.ACTIVE);
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
