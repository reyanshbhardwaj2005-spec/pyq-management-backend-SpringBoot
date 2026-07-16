package com.iiitsonepat.questionbank.specification;

import com.iiitsonepat.questionbank.entity.QuestionPaper;
import com.iiitsonepat.questionbank.enums.Branch;
import com.iiitsonepat.questionbank.enums.ExaminationType;
import com.iiitsonepat.questionbank.enums.PaperStatus;

import org.springframework.data.jpa.domain.Specification;

public class QuestionPaperSpecification {

    public static Specification<QuestionPaper> search(

            Integer semester,

            ExaminationType examinationType,

            Integer academicYear,

            Branch branch

    ){

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            predicate = cb.and(
                    predicate,
                    cb.equal(root.get("status"), PaperStatus.ACTIVE)
            );

            if(semester != null){

                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("semester"), semester)
                );

            }

            if(examinationType != null){

                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("examinationType"), examinationType)
                );

            }

            if(academicYear != null){

                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("academicYear"), academicYear)
                );

            }

            if(branch != null){

                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("branch"), branch)
                );

            }

            return predicate;

        };

    }

}