package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EscalationMasterRepository extends JpaRepository<EscalationMaster,Integer> {

    @Query("SELECT am.id FROM AppraisalMaster am WHERE am.employeeId = :employeeId AND am.isActive = true")
    Integer findAppraisalIdByEmployeeId(Integer employeeId);
}
