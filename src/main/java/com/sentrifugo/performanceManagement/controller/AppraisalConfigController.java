package com.sentrifugo.performanceManagement.controller;




import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.repository.AppraisalConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appraisalsConfig")
public class AppraisalConfigController {

    @Autowired
    private AppraisalConfigRepository appraisalConfigRepository;

    @GetMapping("/getallAppraisals")
    public List<AppraisalConfig> getAllAppraisalConfig() {return appraisalConfigRepository.findAll();}


}

