package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AppraisalMasterRepository extends JpaRepository<AppraisalMaster, Long> {


    @Query("SELECT am.status FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
    String findStatusById(Long id);

    @Query("SELECT am FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
    AppraisalMaster findStatusrowById(Long id);

    //finds the status of the active appraisal of the employee
    @Query("SELECT am FROM AppraisalMaster am WHERE am.employeeId = :employeeId AND am.isActive = :isActive")
    List<AppraisalMaster> findByEmployeeIdAndActive(Long employeeId, boolean isActive);


    Optional<AppraisalMaster> findByEmployeeIdAndIsActive(Long employeeId, boolean b);


    @Query("select Id from AppraisalMaster where employeeId=:id and isActive=true")
    Integer findAppraisalMasterId(Integer id);

    @Query("SELECT a.employeeId as empid, u.name AS name, a.status as status, a.updatedDate as updateddate FROM AppraisalMaster a INNER JOIN Users u ON a.employeeId = u.id WHERE a.isActive = true")
    List<Map<String,Object>> findActiveStatusOfEmployee();

}

//    @Query("SELECT am.Id FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
//    Integer findAppraisalIdByEmployeeID(Integer employeeId);



