package com.sentrifugo.performanceManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import com.sentrifugo.performanceManagement.service.AppraisalEmpHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
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


    @GetMapping("getHistoryByEmpId")
    public ResponseEntity<?> getByEmp(@RequestParam(name = "eid") Long eid){
        try{
            List<AppraisalEmpHistory> history=appraisalEmpHistoryService.getByEmpId(eid);
            System.out.println("History"+history.isEmpty());
            if (!history.isEmpty()) {
                return ResponseEntity.ok(history);
            } else {
                Map<String,String> map=new HashMap<>();
                map.put("message","No past appraisals were found");
                List<Map<String,String>> list=new ArrayList<>();
                list.add(map);
                return ResponseEntity.ok(list);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
