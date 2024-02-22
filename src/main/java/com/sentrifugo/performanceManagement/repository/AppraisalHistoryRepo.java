package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  AppraisalHistoryRepo extends JpaRepository<SelfAssessment,Integer> {

//    @Query("SELECT  (am.employeeComments, am.managerComments, am.employeeRating, am.managerRating, am.status,am.question) " +
//            "FROM appraisal_master_ext am " +
//            "WHERE am.appraisalMasterId = :id")
//    List<AppraisalDetailsDto> findDetailsById(@Param("id") Integer id);

    List<SelfAssessment> findByappraisalMasterId(Integer appraisalMasterId);




}
