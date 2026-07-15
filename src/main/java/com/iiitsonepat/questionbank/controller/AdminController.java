package com.iiitsonepat.questionbank.controller;

import com.iiitsonepat.questionbank.dto.request.UpdatePaperRequest;
import com.iiitsonepat.questionbank.dto.request.UploadPaperRequest;
import com.iiitsonepat.questionbank.dto.resopnse.ApiResponse;
import com.iiitsonepat.questionbank.dto.resopnse.PaperResponse;
import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.provider.QuestionPaperProvider;
import com.iiitsonepat.questionbank.service.QuestionPaperService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/papers")
public class AdminController {

    private final QuestionPaperService questionPaperService;
    private final QuestionPaperProvider questionPaperProvider;

    public AdminController(QuestionPaperService questionPaperService, QuestionPaperProvider questionPaperProvider) {
        this.questionPaperService = questionPaperService;
        this.questionPaperProvider = questionPaperProvider;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<PaperResponse>> uploadPaper(

            @RequestPart("file") MultipartFile file,
            @Valid
            @ModelAttribute UploadPaperRequest request) {

        PaperResponse response = questionPaperService.uploadPaper(request, file);

        return ResponseEntity.ok(
                ApiResponse.<PaperResponse>builder()
                        .success(true)
                        .message("Question paper uploaded successfully.")
                        .data(response)
                        .build()

        );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<PaperResponse>>> getAllPapers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uploadedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {

        Page<PaperResponse> papers =
                questionPaperService.getAllPapers(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(
                ApiResponse.<Page<PaperResponse>>builder()
                        .success(true)
                        .status(200)
                        .message("Question papers fetched successfully.")
                        .data(papers)
                        .timestamp(java.time.LocalDateTime.now())
                        .build()

        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaperResponse>> getPaperById(
            @PathVariable Long id) {

        PaperResponse response =
                questionPaperService.getPaperById(id);

        return ResponseEntity.ok(

                ApiResponse.<PaperResponse>builder()
                        .success(true)
                        .status(200)
                        .message("Question paper fetched successfully.")
                        .data(response)
                        .timestamp(java.time.LocalDateTime.now())
                        .build()

        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePaper(
            @PathVariable Long id) {

        questionPaperService.deletePaper(id);

        return ResponseEntity.ok(

                ApiResponse.<Void>builder()
                        .success(true)
                        .status(200)
                        .message("Question paper deleted successfully.")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build()

        );

    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PaperResponse>> updatePaper(

            @PathVariable Long id,
            @Valid
            @ModelAttribute UpdatePaperRequest request,
            @RequestPart(value = "file", required = false)
            MultipartFile file) {

        PaperResponse response = questionPaperService.updatePaper(id, request, file);

        return ResponseEntity.ok(
                ApiResponse.<PaperResponse>builder()
                        .success(true)
                        .status(200)
                        .message("Question paper updated successfully.")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()

        );
    }


    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadPaper(
            @PathVariable Long id) {

        QuestionPaper paper = questionPaperProvider.getActivePaper(id);
        Resource resource = questionPaperService.downloadPaper(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                paper.getOriginalFileName() +
                                "\"")
                .contentLength(paper.getFileSize())
                .body(resource);

    }

}
