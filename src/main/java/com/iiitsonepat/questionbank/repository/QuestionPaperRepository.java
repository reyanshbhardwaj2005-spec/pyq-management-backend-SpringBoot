package com.iiitsonepat.questionbank.repository;

import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.enums.PaperStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long>, JpaSpecificationExecutor<QuestionPaper> {

    // Check duplicate paper
    Optional<QuestionPaper> findBySemesterAndExaminationTypeAndAcademicYearAndBranch(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            Branch branch);

    // Get only active papers
    Page<QuestionPaper> findByStatus(
            PaperStatus status,
            Pageable pageable);

    // Student filter
    Page<QuestionPaper> findBySemesterAndExaminationTypeAndAcademicYearAndBranchAndStatus(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            Branch branch,
            PaperStatus status,
            Pageable pageable);

    Optional<QuestionPaper> findBySemesterAndExaminationTypeAndAcademicYearAndBranchAndStatus(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            Branch branch,
            PaperStatus status);

    @Query("""
       SELECT DISTINCT q.academicYear
       FROM QuestionPaper q
       WHERE q.status = :status
       ORDER BY q.academicYear DESC
       """)
    List<Integer> findDistinctAcademicYears(
            @Param("status") PaperStatus status
    );

    @Query("""
       SELECT DISTINCT q.branch
       FROM QuestionPaper q
       WHERE q.status = :status
       ORDER BY q.branch DESC
       """)
    List<Branch> findDistinctBranches(
            @Param("status") PaperStatus status
    );
}
