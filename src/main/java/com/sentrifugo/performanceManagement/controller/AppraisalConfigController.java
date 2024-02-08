package com.sentrifugo.performanceManagement.controller;



import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.repository.AppraisalConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appraisalsconfig")
public class AppraisalConfigController {

    @Autowired
    private AppraisalConfigRepository appraisalConfigRepository;

    @GetMapping("/getallAppraisals")
    public List<AppraisalConfig> getAllAppraisalConfig() {return appraisalConfigRepository.findAll();}

    @GetMapping("/getappraisal/{id}")
    public ResponseEntity<AppraisalConfig> getAppraisalConfigById(@PathVariable(value = "id") Long appraisalConfigId)
            throws ResourceNotFoundException {
        AppraisalConfig appraisalConfig = appraisalConfigRepository.findById(appraisalConfigId)
                .orElseThrow(() -> new ResourceNotFoundException("Appraisal Config data not found for this id " + appraisalConfigId));
        return ResponseEntity.ok().body(appraisalConfig);
    }

    @PostMapping("/postappraisal")
    public AppraisalConfig createAppraisalConfig(@RequestBody AppraisalConfig appraisalConfig){
        System.out.println(appraisalConfig);
        return appraisalConfigRepository.save(appraisalConfig);
    }

    @PutMapping("/putappraisal/{id}")
    public ResponseEntity<AppraisalConfig> updateAppraisalConfig(@PathVariable(value = "id")Long appraisalConfigId,
                                                                 @RequestBody AppraisalConfig appraisalConfigdetails) throws ResourceNotFoundException {
        AppraisalConfig appraisalConfig =appraisalConfigRepository.findById(appraisalConfigId)
                .orElseThrow(()->new ResourceNotFoundException("Appraisal configs not found for this id :: "+ appraisalConfigId));
        appraisalConfig.setAppraisalMode(appraisalConfigdetails.getAppraisalMode());
        appraisalConfig.setWorkflowConfigId(appraisalConfigdetails.getWorkflowConfigId());
        appraisalConfig.setDepartment(appraisalConfigdetails.getDepartment());
        appraisalConfig.setEligibility(appraisalConfigdetails.getEligibility());
        appraisalConfig.setBusinessUnit(appraisalConfigdetails.getBusinessUnit());
        appraisalConfig.setEmployeeDueDate(appraisalConfigdetails.getEmployeeDueDate());
        appraisalConfig.setEnableTo(appraisalConfigdetails.getEnableTo());
        appraisalConfig.setManagerDueDate(appraisalConfigdetails.getManagerDueDate());
        appraisalConfig.setFromYear(appraisalConfigdetails.getFromYear());
        appraisalConfig.setParameter(appraisalConfigdetails.getParameter());
        appraisalConfig.setRating(appraisalConfigdetails.getRating());
        appraisalConfig.setPeriod(appraisalConfigdetails.getPeriod());
        appraisalConfig.setProcessStatus(appraisalConfigdetails.getProcessStatus());
        appraisalConfig.setToYear(appraisalConfigdetails.getToYear());
        appraisalConfig.setStatus(appraisalConfigdetails.getStatus());
        final AppraisalConfig updatedAppraisalConfig = appraisalConfigRepository.save(appraisalConfig);
        return ResponseEntity.ok(updatedAppraisalConfig);
    }

    @DeleteMapping("/deleteappraisal/{id}")
    public Map<String, Boolean> deleteAppraisalConfig(@PathVariable(value = "id") Long appraisalConfigId)
        throws ResourceNotFoundException{
        AppraisalConfig appraisalConfig =appraisalConfigRepository.findById((appraisalConfigId))
                .orElseThrow(()-> new ResourceNotFoundException("Appraisal configs not found for this id :: "+ appraisalConfigId));

        appraisalConfigRepository.delete(appraisalConfig);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}

