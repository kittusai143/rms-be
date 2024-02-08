package com.sentrifugo.performanceManagement.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrifugo.performanceManagement.entity.WorkFlow;
import com.sentrifugo.performanceManagement.repository.WorkFlowConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin("*")
@Service
public class WorkFlowService {

    @Autowired
    private  WorkFlowConfigRepository workFlowRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public WorkFlow save(WorkFlow workFlow) {
        try {
            return workFlowRepository.save(workFlow);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkFlow> getAllWorkFlows() {
        return workFlowRepository.findAll();
    }
}
