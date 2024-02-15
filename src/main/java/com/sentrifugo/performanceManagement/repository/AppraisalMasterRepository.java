package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppraisalMasterRepository extends JpaRepository<AppraisalMaster, Long> {


    @Query("SELECT am.status FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
    String findStatusById(Long id);
    //finds the status of the active appraisal of the employee

    @Query("SELECT am FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
    AppraisalMaster findStatusrowById(Long id);

    Optional<AppraisalMaster> findByEmployeeIdAndIsActive(Long employeeId, boolean b);


}

//    @Query("SELECT am.Id FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
//    Integer findAppraisalIdByEmployeeID(Integer employeeId);



