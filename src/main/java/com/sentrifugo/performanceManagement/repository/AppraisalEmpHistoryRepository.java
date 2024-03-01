package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppraisalEmpHistoryRepository extends JpaRepository<AppraisalEmpHistory, Long> {
    @Query("SELECT a FROM AppraisalEmpHistory a WHERE a.appraisalMasId = ?1")
    List<AppraisalEmpHistory> findByAppraisalMasId(Long appraisalMasId);
}

