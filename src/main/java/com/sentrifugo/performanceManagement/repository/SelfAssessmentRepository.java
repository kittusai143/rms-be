package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelfAssessmentRepository extends JpaRepository<SelfAssessment,Integer> {

          List<SelfAssessment> findByAppraisalMasterId(Integer appraisalMasterId);

}
