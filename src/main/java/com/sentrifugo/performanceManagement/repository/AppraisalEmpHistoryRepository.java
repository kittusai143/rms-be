package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AppraisalEmpHistoryRepository extends JpaRepository<AppraisalEmpHistory, Long> {
    @Query("SELECT a FROM AppraisalEmpHistory a WHERE a.appraisalMasId = :appraisalMasId")
    List<AppraisalEmpHistory> findByAppraisalMasId(Long appraisalMasId);

    @Query("SELECT a, u.name FROM AppraisalEmpHistory a JOIN Users u ON a.empId = u.id WHERE YEAR(a.date) = YEAR(:date) AND MONTH(a.date) = MONTH(:date) AND DAY(a.date) = DAY(:date)")
    List<AppraisalEmpHistory> findByDate(@Param("date") Date date);

}

