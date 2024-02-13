package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.appraisal_master_ext;
import com.sentrifugo.performanceManagement.vo.AppraisalDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface  AppraisalHistoryRepo extends JpaRepository<appraisal_master_ext,Integer> {

//    @Query("SELECT  (am.employeeComments, am.managerComments, am.employeeRating, am.managerRating, am.status,am.question) " +
//            "FROM appraisal_master_ext am " +
//            "WHERE am.appraisalMasterId = :id")
//    List<AppraisalDetailsDto> findDetailsById(@Param("id") Integer id);

    List<appraisal_master_ext> findByappraisalMasterId(Integer appraisalMasterId);




}
