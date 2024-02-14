package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppraisalMasterService {
    @Autowired
    AppraisalMasterRepository appraisalMasterRepository;

}