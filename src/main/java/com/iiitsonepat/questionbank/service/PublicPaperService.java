package com.iiitsonepat.questionbank.service;

import com.iiitsonepat.questionbank.dto.resopnse.StudentPaperResponse;
import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PublicPaperService {

    List<StudentPaperResponse> searchPapers(Integer semester, ExaminationType examinationType, Integer academicYear, Branch branch);
    Resource downloadPaper(Long id);
    List<Integer> getAcademicYears();
    List<Branch> getBranches();
    List<ExaminationType> getExaminationTypes();
    List<Integer> getSemesters();
}
