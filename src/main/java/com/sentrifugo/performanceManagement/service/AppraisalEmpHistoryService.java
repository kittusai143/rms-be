package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import com.sentrifugo.performanceManagement.repository.AppraisalEmpHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
