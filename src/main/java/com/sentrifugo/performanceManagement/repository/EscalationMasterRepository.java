package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface EscalationMasterRepository extends JpaRepository<EscalationMaster,Integer> {

    @Query("SELECT am.id FROM AppraisalMaster am WHERE am.employeeId = :employeeId AND am.isActive = true")
    Integer findAppraisalIdByEmployeeId(Integer employeeId);

    @Query(value = " select u.name, u.employeeId,u.email,u.contactNumber,e.department,em.comments,em.reason,em.initiated_by,em.l2_manager_comments,em.appraisal_master_id from escalation_master em join appraisal_master am on am.Id = em.appraisal_master_id  join  employee e  on e.Id = am.employee_id join Users u on u.Id = e.user_id  where appraisal_master_id = :id" ,nativeQuery = true)
    Map<String,Object> findEscalationInDetailViewbyId(Integer id);

    @Query(value = "SELECT em.appraisal_master_id , am.employee_id , e.businessunit , e.department , u.name AS user_name , em.initiated_by , em.status FROM [performance_appraisal].[dbo].[escalation_master] em INNER JOIN  [performance_appraisal].[dbo].[appraisal_master] am ON em.appraisal_master_id = am.Id  INNER JOIN  [performance_appraisal].[dbo].[employee] e ON am.employee_id = e.Id  INNER JOIN  [performance_appraisal].[dbo].[Users] u ON u.Id = e.user_id  WHERE  am.isActive = 1", nativeQuery = true)
    List<Object[]> findAllDetails();

    @Query(value = "SELECT em.appraisal_master_id , am.employee_id , e.businessunit , e.department , u.name AS user_name , em.initiated_by , em.status FROM [performance_appraisal].[dbo].[escalation_master] em INNER JOIN  [performance_appraisal].[dbo].[appraisal_master] am ON em.appraisal_master_id = am.Id  INNER JOIN  [performance_appraisal].[dbo].[employee] e ON am.employee_id = e.Id  INNER JOIN  [performance_appraisal].[dbo].[Users] u ON u.Id = e.user_id  WHERE  am.isActive = 1 and department=:department " , nativeQuery = true)
    List<Object[]> findAllDetailsByDepartment(String department);

    @Query(value = "SELECT em.appraisal_master_id , am.employee_id , e.businessunit , e.department , u.name AS user_name , em.initiated_by , em.status FROM [performance_appraisal].[dbo].[escalation_master] em INNER JOIN  [performance_appraisal].[dbo].[appraisal_master] am ON em.appraisal_master_id = am.Id  INNER JOIN  [performance_appraisal].[dbo].[employee] e ON am.employee_id = e.Id  INNER JOIN  [performance_appraisal].[dbo].[Users] u ON u.Id = e.user_id  WHERE  am.isActive = 1 and businessunit=:businessunit" , nativeQuery = true)
    List<Object[]> findAllDetailsByDesignation(String businessunit);
    @Query(value = "SELECT em.appraisal_master_id , am.employee_id , e.businessunit , e.department , u.name AS user_name , em.initiated_by , em.status FROM [performance_appraisal].[dbo].[escalation_master] em INNER JOIN  [performance_appraisal].[dbo].[appraisal_master] am ON em.appraisal_master_id = am.Id  INNER JOIN  [performance_appraisal].[dbo].[employee] e ON am.employee_id = e.Id  INNER JOIN  [performance_appraisal].[dbo].[Users] u ON u.Id = e.user_id  WHERE  am.isActive = 1 and em.status=:status ", nativeQuery = true)
    List<Object[]> findAllDetailsByStatus(String status);


    @Transactional
    @Modifying
    @Query("UPDATE EscalationMaster e SET e.l2ManagerComments = :comments WHERE e.appraisalMasterId = :appraisalMasterId")
    int hrCommentsSaving(Integer appraisalMasterId, String comments);


    @Transactional
    @Modifying
    @Query("UPDATE EscalationMaster e SET e.status = :str WHERE e.appraisalMasterId = :id")
    void hrStatusChange(Integer id, String str);

    EscalationMaster findByAppraisalMasterId(Integer id);

}
