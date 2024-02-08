package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.service.SelfAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sa")
@CrossOrigin
public class SelfAssessmentController {

    private final SelfAssessmentService selfAssessmentService;

    @Autowired
    public SelfAssessmentController(SelfAssessmentService selfAssessmentService) {
        this.selfAssessmentService = selfAssessmentService;
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
    @PostMapping("/submit")
    public ResponseEntity<List<SelfAssessment>> submitSelfAssessmentForm(@RequestBody List<SelfAssessment> assessments) {
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

    @PutMapping("/update/{id}")
    public ResponseEntity<SelfAssessment> updateSelfAssessment(@PathVariable Integer id, @RequestBody SelfAssessment updatedAssessment) {
        SelfAssessment updatedAssessmentResult = selfAssessmentService.updateSelfAssessment(id, updatedAssessment);
        return new ResponseEntity<>(updatedAssessmentResult, HttpStatus.OK);
    }



}
