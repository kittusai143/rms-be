package com.sentrifugo.performanceManagement.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.service.AppraisalMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/appraisal")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class AppraisalMasterController {

    @Autowired
    private AppraisalMasterRepository appraisalMasterRepository;

    @Autowired
    private AppraisalMasterService appraisalMasterService;

    @GetMapping("/getactivestatusEmp")
    public List<Map<String,Object>> findActiveAppraisalStatus(){
        List<Map<String,Object>> res= appraisalMasterRepository.findActiveStatusOfEmployee();
        return appraisalMasterRepository.findActiveStatusOfEmployee();
    }

    @GetMapping("/getHistoryON/{date}")
    public List<Map<String,Object>> getbydate(@PathVariable(value = "date") Date updatedDate) throws ResourceNotFoundException {
        List<Map<String,Object>> EmpHistoryOn = appraisalMasterService.getbydate(updatedDate);
        return EmpHistoryOn;
    }
}