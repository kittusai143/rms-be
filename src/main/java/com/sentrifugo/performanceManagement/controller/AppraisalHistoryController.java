package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.service.AppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("appraisalhistroy")
public class AppraisalHistoryController {

    @Autowired
    private AppraisalService appraisalService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAppraisalDetails(@PathVariable Integer id) {
        try {
            List<SelfAssessment> appraisalDetailsList = appraisalService.getAppraisalDetailsById(id);
            if (appraisalDetailsList.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                map.put("status", "No record found for ID");
                List<Map<String, String>> lis = new ArrayList<>();
                lis.add(map);
                return ResponseEntity.ok(lis);
            } else {
                return ResponseEntity.ok(appraisalDetailsList);
            }
        }
        catch (Exception e)
        {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/getAppraisalMasterId/{id}")
    public ResponseEntity<?> getAppraisalMasterId(@PathVariable Integer id)
    {
        try {
            Integer appraialId = appraisalService.getAppraisalId(id);
            String message = "No record found for ID";
            if (appraialId == null)
                return ResponseEntity.ok(message);
            else
                return ResponseEntity.ok(appraialId);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
