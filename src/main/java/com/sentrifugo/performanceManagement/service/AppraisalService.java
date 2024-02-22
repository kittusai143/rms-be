package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.AppraisalHistoryRepo;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppraisalService {

        @Autowired
        private AppraisalHistoryRepo Repo;
        public List<SelfAssessment> getAppraisalDetailsById(Integer id) {
              return Repo.findByappraisalMasterId(id);
        }

    @Autowired
    private AppraisalMasterRepository Repo1;
    //to get the appraisalMasterId Based On the Employee Role
    public Integer getAppraisalId(Integer id)
    {
        return Repo1.findAppraisalMasterId(id);
    }
}

