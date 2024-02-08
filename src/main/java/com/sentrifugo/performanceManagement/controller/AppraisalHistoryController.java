package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.service.AppraisalService;
import com.sentrifugo.performanceManagement.vo.AppraisalDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("appraisalhistroy")
public class AppraisalHistoryController {

    @Autowired
    private AppraisalService appraisalService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAppraisalDetails(@PathVariable Integer id) {
        List<AppraisalDetailsDto> appraisalDetailsList = appraisalService.getAppraisalDetailsById(id);
        if (appraisalDetailsList.isEmpty()) {
            String message = "No record found for ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(appraisalDetailsList);
        }
    }
}
