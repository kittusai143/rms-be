package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppraisalMasterRepository extends JpaRepository<AppraisalMaster, Long> {


    @Query("SELECT am.status FROM AppraisalMaster am WHERE am.employeeId = :id AND am.isActive = true")
    String findStatusById(Long id);
    @Query("SELECT am FROM AppraisalMaster am WHERE am.employeeId = :employeeId AND am.isActive = :isActive")
    List<AppraisalMaster> findByEmployeeIdAndActive(Long employeeId, boolean isActive);

    Optional<AppraisalMaster> findByEmployeeIdAndIsActive(Long employeeId, boolean b);
}
