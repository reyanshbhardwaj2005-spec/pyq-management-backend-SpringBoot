package com.iiitsonepat.questionbank.repository;

import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.enums.PaperStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long> {

    // Check duplicate paper
    Optional<QuestionPaper> findBySemesterAndExaminationTypeAndAcademicYearAndBatch(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            String batch);

    // Get only active papers
    Page<QuestionPaper> findByStatus(
            PaperStatus status,
            Pageable pageable);

    // Student filter
    Page<QuestionPaper> findBySemesterAndExaminationTypeAndAcademicYearAndBatchAndStatus(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            String batch,
            PaperStatus status,
            Pageable pageable);

    Optional<QuestionPaper> findBySemesterAndExaminationTypeAndAcademicYearAndBatchAndStatus(
            Integer semester,
            ExaminationType examinationType,
            Integer academicYear,
            String batch,
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
       SELECT DISTINCT q.batch
       FROM QuestionPaper q
       WHERE q.status = :status
       ORDER BY q.batch DESC
       """)
    List<String> findDistinctBatches(
            @Param("status") PaperStatus status
    );
}
