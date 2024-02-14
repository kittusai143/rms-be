package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.service.SelfAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sa")
@CrossOrigin("*")
public class SelfAssessmentController {

    private final SelfAssessmentService selfAssessmentService;

    @Autowired
    public SelfAssessmentController(SelfAssessmentService selfAssessmentService) {
        this.selfAssessmentService = selfAssessmentService;
    }



    @GetMapping("/am-status/{eid}")
    public AppraisalMaster getamStatus(@PathVariable Integer eid){
        return selfAssessmentService.getamStatus(eid);
    }

    @GetMapping("/status/{eid}")
    public String getStatus(@PathVariable Integer eid){
        return selfAssessmentService.getStatus(eid);
    }

    @GetMapping("/activemId/{eid}")
    public Long getActivemid(@PathVariable Long eid) {
        return selfAssessmentService.getActiveAppraisalMasterId(eid);
    }

    @PutMapping("/status/{mid}/{status}")
    public String changeStatus(@PathVariable Integer mid, @PathVariable String status) {
        return selfAssessmentService.changeStatus(mid, status);
    }


    @GetMapping("/get-all-rows")
    public ResponseEntity<List<SelfAssessment>> getSelfAssessmentForm() {
        try {
            List<SelfAssessment> selfAssessmentForm = selfAssessmentService.getSelfAssessmentForm();
            return new ResponseEntity<>(selfAssessmentForm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all-rows/{mid}")
    public ResponseEntity<List<SelfAssessment>> getSelfAssessmentFormbyId(@PathVariable Integer mid) {
        try {
            List<SelfAssessment> selfAssessmentForm = selfAssessmentService.getSelfAssessmentFormByMasterId(mid);
            return new ResponseEntity<>(selfAssessmentForm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



        @PostMapping("/submit")
    public ResponseEntity<List<SelfAssessment>> submitSelfAssessmentForm(@RequestBody List<SelfAssessment> assessments) {
        System.out.println(assessments);
        List<SelfAssessment> submittedAssessments = selfAssessmentService.submit(assessments);
        return new ResponseEntity<>(submittedAssessments, HttpStatus.CREATED);
    }

    @PostMapping("/add-goal/{question}")
    public ResponseEntity<SelfAssessment> addQuestion(@PathVariable String question) {
        try {
            SelfAssessment newAssessment = new SelfAssessment();
            newAssessment.setQuestion(question);
            selfAssessmentService.addRow(newAssessment);
            return new ResponseEntity<>(newAssessment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/getbyId/{id}")
      public ResponseEntity<SelfAssessment> getSelfAssessmentById(@PathVariable Integer id) {
      SelfAssessment selfAssessment = selfAssessmentService.getSelfAssessmentById(id);

      if (selfAssessment != null) {
          return new ResponseEntity<>(selfAssessment, HttpStatus.OK);
      } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteSelfAssessmentById(@PathVariable Integer id) {
        boolean deleted = selfAssessmentService.deleteSelfAssessmentById(id);

        if (deleted) {
            return new ResponseEntity<>("Question with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getbymid/{mid}")
    public ResponseEntity<List<SelfAssessment>> getSelfAssessmentsByMid(@PathVariable Integer mid) {
        List<SelfAssessment> selfAssessments = selfAssessmentService.getSelfAssessmentbymid(mid);

        if (!selfAssessments.isEmpty()) {
            return new ResponseEntity<>(selfAssessments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SelfAssessment> updateSelfAssessment(@PathVariable Integer id, @RequestBody SelfAssessment updatedAssessment) {
        SelfAssessment updatedAssessmentResult = selfAssessmentService.updateSelfAssessment(id, updatedAssessment);
        return new ResponseEntity<>(updatedAssessmentResult, HttpStatus.OK);
    }

//    @GetMapping("/initialize/{employeeId}")
//    public ResponseEntity<String> initializeAssessment(@PathVariable Integer employeeId) {
//        try {
//            selfAssessmentService.initializeAssessment(employeeId);
//            return new ResponseEntity<>("Assessment initialized successfully.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to initialize assessment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



}
