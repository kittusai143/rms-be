package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppraisalMasterRepository extends JpaRepository<AppraisalMaster, Long> {


    @Query("SELECT am.status FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
    String findStatusById(Long id);


    Optional<AppraisalMaster> findByEmployeeIdAndIsActive(Long employeeId, boolean b);

    @Query(value = "select id FROM performance_appraisal.dbo.appraisal_masterwhere employee_id = 11 and isactive = 1",nativeQuery = true)
    Integer findAppraisalIdByEmployeeID(Integer employeeId);
}
