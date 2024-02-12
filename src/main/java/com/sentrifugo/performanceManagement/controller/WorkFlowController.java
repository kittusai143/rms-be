package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.WorkFlow;
import com.sentrifugo.performanceManagement.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workFlowConfig")
@CrossOrigin
public class WorkFlowController {
@Autowired
private  WorkFlowService workFlowService;

    @PostMapping("/workflow")
    public ResponseEntity<WorkFlow> saveWorkflow(@RequestBody WorkFlow workFlow) {
        WorkFlow savedWorkFlow = workFlowService.save(workFlow);
        if (savedWorkFlow != null) {
            return new ResponseEntity<>(savedWorkFlow, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getworkflow")
    public ResponseEntity<List<WorkFlow>> getAllWorkFlows() {
        List<WorkFlow> workFlows = workFlowService.getAllWorkFlows();
        if (!workFlows.isEmpty()) {
            return new ResponseEntity<>(workFlows, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
