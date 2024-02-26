package com.sentrifugo.performanceManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.service.AppraisalConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appraisalsconfig")
@CrossOrigin
public class AppraisalConfigController {

    private final AppraisalConfigService appraisalConfigService;

    @Autowired
    public AppraisalConfigController(AppraisalConfigService appraisalConfigService) {
        this.appraisalConfigService = appraisalConfigService;
    }

    @GetMapping("/getallAppraisals")
    public List<AppraisalConfig> getAllAppraisalConfig() {
        return appraisalConfigService.getAllAppraisalConfig();
    }

    @GetMapping("/getappraisal/{id}")
    public ResponseEntity<AppraisalConfig> getAppraisalConfigById(@PathVariable(value = "id") Long appraisalConfigId)
            throws ResourceNotFoundException {
        AppraisalConfig appraisalConfig = appraisalConfigService.getAppraisalConfigById(appraisalConfigId);
        return ResponseEntity.ok().body(appraisalConfig);
    }

    @PostMapping("/postappraisal")
    public AppraisalConfig createAppraisalConfig(@RequestBody Map<String, Object> requestBody) {
        // Convert department array of objects to comma-separated string
        List<Map<String, String>> departments = (List<Map<String, String>>) requestBody.get("department");
        String departmentString = departments.stream()
                .map(dept -> dept.get("value"))
                .collect(Collectors.joining(", "));

        // Replace the department field in the request body with the string value
        requestBody.put("department", departmentString);

        // Convert the modified request body to AppraisalConfig object
        ObjectMapper objectMapper = new ObjectMapper();
        AppraisalConfig appraisalConfig = objectMapper.convertValue(requestBody, AppraisalConfig.class);

        return appraisalConfigService.createAppraisalConfig(appraisalConfig);
    }

    @PutMapping("/putappraisal/{id}")
    public ResponseEntity<AppraisalConfig> updateAppraisalConfig(@PathVariable(value = "id") Long appraisalConfigId,
                                                                 @RequestBody AppraisalConfig appraisalConfigdetails)
            throws ResourceNotFoundException {
        AppraisalConfig updatedAppraisalConfig = appraisalConfigService.updateAppraisalConfig(appraisalConfigId, appraisalConfigdetails);
        return ResponseEntity.ok(updatedAppraisalConfig);
    }

    @DeleteMapping("/deleteappraisal/{id}")
    public Map<String, Boolean> deleteAppraisalConfig(@PathVariable(value = "id") Long appraisalConfigId)
            throws ResourceNotFoundException {
        appraisalConfigService.deleteAppraisalConfig(appraisalConfigId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

