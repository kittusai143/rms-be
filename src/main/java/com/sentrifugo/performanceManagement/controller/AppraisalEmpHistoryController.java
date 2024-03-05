package com.sentrifugo.performanceManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import com.sentrifugo.performanceManagement.service.AppraisalEmpHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appraisalsHistory")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class AppraisalEmpHistoryController {
    @Autowired
    private AppraisalEmpHistoryService appraisalEmpHistoryService;
    @GetMapping("/getHistOF/{mastId}")
    public List<AppraisalEmpHistory> getbymasterId(@PathVariable(value = "mastId") Long appraisalMastId)
            throws ResourceNotFoundException {
        List<AppraisalEmpHistory> Emphistory = appraisalEmpHistoryService.getbyMastId(appraisalMastId);
        System.out.println(Emphistory);

        return Emphistory;
    }

    @GetMapping("/getHistoryON/{date}")
    public List<Map<String,Object>> getbydate(@PathVariable(value = "date")Date date) throws ResourceNotFoundException {
        List<Map<String,Object>> EmpHistoryOn = appraisalEmpHistoryService.getbydate(date);
        return EmpHistoryOn;
    }

    @PostMapping("/addAppraisalHist")
    public AppraisalEmpHistory createAppraisalConfig(@RequestBody AppraisalEmpHistory appraisalEmpHistory) {

        return appraisalEmpHistoryService.createAppraisalEmpHistory(appraisalEmpHistory);
    }

}
