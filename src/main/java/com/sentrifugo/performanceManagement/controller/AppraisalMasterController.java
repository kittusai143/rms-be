package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/appraisal")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class AppraisalMasterController {

    @Autowired
    private AppraisalMasterRepository appraisalMasterRepository;
    @GetMapping("/getactivestatusEmp")
    public List<Map<String,Object>> findActiveAppraisalStatus(){
        List<Map<String,Object>> res= appraisalMasterRepository.findActiveStatusOfEmployee();
        System.out.println(res);
        return appraisalMasterRepository.findActiveStatusOfEmployee();
    }
}
