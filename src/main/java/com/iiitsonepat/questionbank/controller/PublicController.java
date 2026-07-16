package com.iiitsonepat.questionbank.controller;

import com.iiitsonepat.questionbank.dto.resopnse.ApiResponse;
import com.iiitsonepat.questionbank.dto.resopnse.StudentPaperResponse;
import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.provider.QuestionPaperProvider;
import com.iiitsonepat.questionbank.service.PublicPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/public/papers")
@RequiredArgsConstructor
public class PublicController {

    private final PublicPaperService publicPaperService;
    private final QuestionPaperProvider questionPaperProvider;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentPaperResponse>>> searchPapers(

            @RequestParam(required = false)
            Integer semester,

            @RequestParam(required = false)
            ExaminationType examinationType,

            @RequestParam(required = false)
            Integer academicYear,

            @RequestParam(required = false)
            Branch branch

    ) {

        List<StudentPaperResponse> papers =
                publicPaperService.searchPapers(
                        semester,
                        examinationType,
                        academicYear,
                        branch
                );

        return ResponseEntity.ok(

                ApiResponse.<List<StudentPaperResponse>>builder()
                        .success(true)
                        .status(200)
                        .message("Question papers fetched successfully.")
                        .data(papers)
                        .timestamp(LocalDateTime.now())
                        .build()

        );

    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadPaper(@PathVariable Long id) {

        QuestionPaper paper = questionPaperProvider.getActivePaper(id);
        Resource resource = publicPaperService.downloadPaper(id);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + paper.getOriginalFileName() + "\"")
                .contentLength(paper.getFileSize())
                .body(resource);
    }

    @GetMapping("/filters/academic-years")
    public ResponseEntity<ApiResponse<List<Integer>>> getAcademicYears() {

        return ResponseEntity.ok(ApiResponse.<List<Integer>>builder()
                        .success(true)
                        .status(200)
                        .message("Academic years fetched successfully.")
                        .data(publicPaperService.getAcademicYears())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/filters/branches")
    public ResponseEntity<ApiResponse<List<Branch>>> getBatches() {

        return ResponseEntity.ok(ApiResponse.<List<Branch>>builder()
                        .success(true)
                        .status(200)
                        .message("Batches fetched successfully.")
                        .data(publicPaperService.getBranches())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/filters/examination-types")
    public ResponseEntity<ApiResponse<List<ExaminationType>>> getExaminationTypes() {

        return ResponseEntity.ok(ApiResponse.<List<ExaminationType>>builder()
                        .success(true)
                        .status(200)
                        .message("Examination types fetched successfully.")
                        .data(publicPaperService.getExaminationTypes())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/filters/semesters")
    public ResponseEntity<ApiResponse<List<Integer>>> getSemesters() {

        return ResponseEntity.ok(ApiResponse.<List<Integer>>builder()
                        .success(true)
                        .status(200)
                        .message("Semesters fetched successfully.")
                        .data(publicPaperService.getSemesters())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
