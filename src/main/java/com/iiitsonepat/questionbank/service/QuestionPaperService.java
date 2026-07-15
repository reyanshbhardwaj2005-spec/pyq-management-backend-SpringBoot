package com.iiitsonepat.questionbank.service;

import com.iiitsonepat.questionbank.dto.request.UpdatePaperRequest;
import com.iiitsonepat.questionbank.dto.request.UploadPaperRequest;
import com.iiitsonepat.questionbank.dto.resopnse.PaperResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionPaperService {

    PaperResponse uploadPaper(UploadPaperRequest request, MultipartFile file);

    Page<PaperResponse> getAllPapers(int page, int size, String sortBy, String direction);

    PaperResponse getPaperById(Long id);

    void deletePaper(Long id);

    PaperResponse updatePaper(Long id, UpdatePaperRequest request, MultipartFile file);

    Resource downloadPaper(Long id);
}
