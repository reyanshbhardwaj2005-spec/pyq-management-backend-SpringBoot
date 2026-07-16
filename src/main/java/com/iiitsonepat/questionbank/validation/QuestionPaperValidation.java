package com.iiitsonepat.questionbank.validation;

import com.iiitsonepat.questionbank.dto.request.UploadPaperRequest;
import com.iiitsonepat.questionbank.exception.DuplicatePaperException;
import com.iiitsonepat.questionbank.repository.QuestionPaperRepository;
import org.springframework.stereotype.Component;
import java.time.Year;

@Component
public class QuestionPaperValidation {
    private final QuestionPaperRepository repository;

    public QuestionPaperValidation(QuestionPaperRepository repository) {
        this.repository = repository;
    }

    public void validate(UploadPaperRequest request) {
        validateAcademicYear(request);
        validateDuplicate(request);
    }

    private void validateAcademicYear(UploadPaperRequest request) {

        int currentYear = Year.now().getValue();
        if (request.getAcademicYear() < 2020 || request.getAcademicYear() > currentYear + 1) {
            throw new IllegalArgumentException("Invalid academic year.");
        }
    }

    private void validateDuplicate(UploadPaperRequest request) {

        boolean exists = repository.findBySemesterAndExaminationTypeAndAcademicYearAndBranch(
                        request.getSemester(),
                        request.getExaminationType(),
                        request.getAcademicYear(),
                        request.getBranch()).isPresent();

        if (exists) {
            throw new DuplicatePaperException("Question paper already exists.");
        }
    }
}
