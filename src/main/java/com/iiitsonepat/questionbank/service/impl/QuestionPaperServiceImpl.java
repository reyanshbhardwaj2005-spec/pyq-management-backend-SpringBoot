package com.iiitsonepat.questionbank.service.impl;

import com.iiitsonepat.questionbank.dto.request.UpdatePaperRequest;
import com.iiitsonepat.questionbank.dto.request.UploadPaperRequest;
import com.iiitsonepat.questionbank.dto.resopnse.FileUploadResponse;
import com.iiitsonepat.questionbank.dto.resopnse.PaperResponse;
import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.PaperStatus;
import com.iiitsonepat.questionbank.factory.QuestionPaperFactory;
import com.iiitsonepat.questionbank.mapper.QuestionPaperMapper;
import com.iiitsonepat.questionbank.provider.QuestionPaperProvider;
import com.iiitsonepat.questionbank.repository.QuestionPaperRepository;
import com.iiitsonepat.questionbank.service.FileStorageService;
import com.iiitsonepat.questionbank.service.QuestionPaperService;
import com.iiitsonepat.questionbank.validation.QuestionPaperValidation;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class QuestionPaperServiceImpl implements QuestionPaperService {

    private final QuestionPaperRepository questionPaperRepository;
    private final FileStorageService fileStorageService;
    private final QuestionPaperValidation validator;
    private final QuestionPaperFactory factory;
    private final QuestionPaperProvider questionPaperProvider;

    public QuestionPaperServiceImpl(QuestionPaperRepository questionPaperRepository, FileStorageService fileStorageService, QuestionPaperValidation validator, QuestionPaperFactory factory, QuestionPaperProvider questionPaperProvider) {
        this.questionPaperRepository = questionPaperRepository;
        this.fileStorageService = fileStorageService;
        this.validator = validator;
        this.factory = factory;
        this.questionPaperProvider = questionPaperProvider;
    }

    @Override
    @Transactional
    public PaperResponse uploadPaper(UploadPaperRequest request, MultipartFile file) {

        validator.validate(request);
        FileUploadResponse uploadedFile = null;

        try {
            uploadedFile = fileStorageService.storeFile(file, request.getAcademicYear(), request.getSemester());

            QuestionPaper paper = factory.create(request, uploadedFile);
            QuestionPaper savedPaper = questionPaperRepository.save(paper);

            return QuestionPaperMapper.toResponse(savedPaper);

        } catch (Exception ex) {

            if (uploadedFile != null) {
                fileStorageService.deleteFile(uploadedFile.getFilePath());
            }
            throw ex;
        }
    }

    @Override
    public Page<PaperResponse> getAllPapers(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return questionPaperRepository
                .findByStatus(PaperStatus.ACTIVE, pageable)
                .map(QuestionPaperMapper::toResponse);
    }

    @Override
    public PaperResponse getPaperById(Long id) {

        QuestionPaper paper = questionPaperProvider.getById(id);

        return QuestionPaperMapper.toResponse(paper);

    }

    @Override
    @Transactional
    public void deletePaper(Long id) {

        QuestionPaper paper = questionPaperProvider.getActivePaper(id);
        paper.setStatus(PaperStatus.DELETED);
        questionPaperRepository.save(paper);
        fileStorageService.deleteFile(paper.getFilePath());

    }

    @Override
    @Transactional
    public PaperResponse updatePaper(Long id, UpdatePaperRequest request, MultipartFile file) {

        QuestionPaper paper = questionPaperProvider.getActivePaper(id);
        factory.updateEntity(paper, request);

        if (file != null && !file.isEmpty()) {

            FileUploadResponse uploaded = fileStorageService.storeFile(file, request.getAcademicYear(), request.getSemester());

            String oldFilePath = paper.getFilePath();

            paper.setFileName(uploaded.getFileName());
            paper.setOriginalFileName(uploaded.getOriginalFileName());
            paper.setFilePath(uploaded.getFilePath());
            paper.setFileSize(uploaded.getFileSize());

            fileStorageService.deleteFile(oldFilePath);
        }

        QuestionPaper updated = questionPaperRepository.save(paper);

        return QuestionPaperMapper.toResponse(updated);

    }

    @Override
    public Resource downloadPaper(Long id) {

        QuestionPaper paper = questionPaperProvider.getActivePaper(id);
        return fileStorageService.loadFileAsResource(
                paper.getFilePath());
    }

}
