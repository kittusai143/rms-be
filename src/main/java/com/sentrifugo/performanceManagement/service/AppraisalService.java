package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.repository.AppraisalHistoryRepo;
import com.sentrifugo.performanceManagement.vo.AppraisalDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppraisalService {

        @Autowired
        private AppraisalHistoryRepo Repo;
        public List<AppraisalDetailsDto> getAppraisalDetailsById(Integer id) {
            return Repo.findDetailsById(id);
        }
    }

