package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface EscalationMasterRepository extends JpaRepository<EscalationMaster,Integer> {

    @Query("SELECT am.id FROM AppraisalMaster am WHERE am.employeeId = :employeeId AND am.isActive = true")
    Integer findAppraisalIdByEmployeeId(Integer employeeId);

    @Query(value = " select u.name, u.employeeId,u.email,u.contactNumber,e.department,em.comments,em.reason,em.initiated_by from escalation_master em join appraisal_master am on am.Id = em.appraisal_master_id  join  employee e  on e.Id = am.employee_id join Users u on u.Id = e.user_id  where em.id = :id" ,nativeQuery = true)
    Map<String,Object> findEscalationInDetailViewbyId(Integer id);
}
