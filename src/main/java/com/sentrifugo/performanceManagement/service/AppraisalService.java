package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.appraisal_master_ext;
import com.sentrifugo.performanceManagement.repository.AppraisalHistoryRepo;
import com.sentrifugo.performanceManagement.vo.AppraisalDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AppraisalService {

        @Autowired
        private AppraisalHistoryRepo Repo;
        public List<appraisal_master_ext> getAppraisalDetailsById(Integer id) {
              return Repo.findByappraisalMasterId(id);
        }
    }

