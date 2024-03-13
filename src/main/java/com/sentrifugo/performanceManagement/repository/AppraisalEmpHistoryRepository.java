package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface AppraisalEmpHistoryRepository extends JpaRepository<AppraisalEmpHistory, Long> {
    @Query("SELECT a FROM AppraisalEmpHistory a WHERE a.appraisalMasId = :appraisalMasId")
    List<AppraisalEmpHistory> findByAppraisalMasId(Long appraisalMasId);

    @Query(value = "SELECT a.appraisal_Hist_id, a.appraisalMasId, a.createdBy, a.date, a.employee_id, a.status, u.name " +
            "FROM appraisalemphistory a " +
            "JOIN users u ON a.employee_id = u.id " +
            "WHERE YEAR(a.date) = YEAR(:date) AND MONTH(a.date) = MONTH(:date) AND DAY(a.date) = DAY(:date)",
            nativeQuery = true)
    List<Map<String,Object>> findByDate(@Param("date") Date date);


    List<AppraisalEmpHistory> findByEmpId(Long id);
}

