package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.appraisal_master_ext;
import com.sentrifugo.performanceManagement.service.AppraisalService;
import com.sentrifugo.performanceManagement.vo.AppraisalDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("appraisalhistroy")
public class AppraisalHistoryController {

    @Autowired
    private AppraisalService appraisalService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAppraisalDetails(@PathVariable Integer id) {
        List<appraisal_master_ext> appraisalDetailsList = appraisalService.getAppraisalDetailsById(id);
        if (appraisalDetailsList.isEmpty()) {
            String message = "No record found for ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(appraisalDetailsList);
        }
    }
    @GetMapping("/getAppraisalMasterId/{id}")
    public ResponseEntity<?> getAppraisalMasterId(@PathVariable Integer id)
    {
        Integer appraialId=appraisalService.getAppraisalId(id);
        String message = "No record found for ID: " + id;
        if(appraialId==null)
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        else
            return  ResponseEntity.ok(appraialId);
    }

}
