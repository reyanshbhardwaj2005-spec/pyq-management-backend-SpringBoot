package com.iiitsonepat.questionbank.provider;

import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.enums.PaperStatus;
import com.iiitsonepat.questionbank.exception.ResourceNotFoundException;
import com.iiitsonepat.questionbank.repository.QuestionPaperRepository;
import org.springframework.stereotype.Component;
import com.iiitsonepat.questionbank.enums.Branch;

@Component
public class QuestionPaperProvider {

    private final QuestionPaperRepository repository;

    public QuestionPaperProvider(QuestionPaperRepository repository) {
        this.repository = repository;
    }

    public QuestionPaper getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question paper not found with id : " + id));
    }

    public QuestionPaper getActivePaper(Long id) {
        QuestionPaper paper = getById(id);
        if (paper.getStatus() == PaperStatus.DELETED) {
            throw new ResourceNotFoundException(
                    "Question paper not found.");
        }
        return paper;
    }

    public QuestionPaper getActivePaper(Integer semester, ExaminationType examinationType, Integer academicYear, Branch branch){
        return repository.findBySemesterAndExaminationTypeAndAcademicYearAndBranchAndStatus(
                        semester,
                        examinationType,
                        academicYear,
                        branch,
                        PaperStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("Question paper not found."));
    }
}