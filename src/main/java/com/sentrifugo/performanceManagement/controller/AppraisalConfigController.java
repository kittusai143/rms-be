//package com.sentrifugo.performanceManagement.controller;
//
//import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
//import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
//import com.sentrifugo.performanceManagement.service.AppraisalConfigService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/appraisalsconfig")
//@CrossOrigin
//public class AppraisalConfigController {
//
//    private final AppraisalConfigService appraisalConfigService;
//
//    @Autowired
//    public AppraisalConfigController(AppraisalConfigService appraisalConfigService) {
//        this.appraisalConfigService = appraisalConfigService;
//    }
//
//    @GetMapping("/getallAppraisals")
//    public List<AppraisalConfig> getAllAppraisalConfig() {
//        return appraisalConfigService.getAllAppraisalConfig();
//    }
//
//    @GetMapping("/getappraisal/{id}")
//    public ResponseEntity<AppraisalConfig> getAppraisalConfigById(@PathVariable(value = "id") Long appraisalConfigId)
//            throws ResourceNotFoundException {
//        AppraisalConfig appraisalConfig = appraisalConfigService.getAppraisalConfigById(appraisalConfigId);
//        return ResponseEntity.ok().body(appraisalConfig);
//    }
//
//    @PostMapping("/postappraisal")
//    public AppraisalConfig createAppraisalConfig(@RequestBody AppraisalConfig appraisalConfig) {
//
//        return appraisalConfigService.createAppraisalConfig(appraisalConfig);
//    }
//
//    @PutMapping("/putappraisal/{id}")
//    public ResponseEntity<AppraisalConfig> updateAppraisalConfig(@PathVariable(value = "id") Long appraisalConfigId,
//                                                                 @RequestBody AppraisalConfig appraisalConfigdetails)
//            throws ResourceNotFoundException {
//        AppraisalConfig updatedAppraisalConfig = appraisalConfigService.updateAppraisalConfig(appraisalConfigId, appraisalConfigdetails);
//        return ResponseEntity.ok(updatedAppraisalConfig);
//    }
//
//    @DeleteMapping("/deleteappraisal/{id}")
//    public Map<String, Boolean> deleteAppraisalConfig(@PathVariable(value = "id") Long appraisalConfigId)
//            throws ResourceNotFoundException {
//        appraisalConfigService.deleteAppraisalConfig(appraisalConfigId);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
//}
//
