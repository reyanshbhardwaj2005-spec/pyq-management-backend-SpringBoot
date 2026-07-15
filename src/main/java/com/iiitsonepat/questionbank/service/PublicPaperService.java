package com.iiitsonepat.questionbank.service;

import com.iiitsonepat.questionbank.dto.resopnse.StudentPaperResponse;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PublicPaperService {

    StudentPaperResponse searchPaper(Integer semester, ExaminationType examinationType, Integer academicYear, String batch);
    Resource downloadPaper(Long id);
    List<Integer> getAcademicYears();
    List<String> getBatches();
    List<ExaminationType> getExaminationTypes();
    List<Integer> getSemesters();
}
