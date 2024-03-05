package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import com.sentrifugo.performanceManagement.repository.AppraisalEmpHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class AppraisalEmpHistoryService {

    @Autowired
    private AppraisalEmpHistoryRepository repo;
    public AppraisalEmpHistory createAppraisalEmpHistory(AppraisalEmpHistory appraisalEmpHistory) {
        AppraisalEmpHistory savedAppraisalEmpHistory = repo.save(appraisalEmpHistory);
        System.out.println("Saved to History : "+savedAppraisalEmpHistory);
        return savedAppraisalEmpHistory;

    }
    public List<AppraisalEmpHistory> getbyMastId(Long id){
        return repo.findByAppraisalMasId(id);
    }

    public List<Map<String,Object>> getbydate(Date date){

        // Convert java.util.Date to java.sql.Date with time part set to 00:00:00
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        java.sql.Date mdate = new java.sql.Date(cal.getTimeInMillis());
        return  repo.findByDate(mdate);
    }
}
