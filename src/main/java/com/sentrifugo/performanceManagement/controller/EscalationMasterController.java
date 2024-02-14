package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import com.sentrifugo.performanceManagement.service.EscalationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/escalateMaster")
@CrossOrigin(origins = "*")

public class EscalationMasterController {

    @Autowired
    EscalationMasterService escalationMasterService;

    @PostMapping("/addEscalation/{employeeId}")
    public EscalationMaster addEscalationMaster(@RequestBody EscalationMaster escalationMaster,@PathVariable("employeeId")Integer employeeId){
        return escalationMasterService.addEscalationMaster(escalationMaster,employeeId);
    }

//    @GetMapping("/getEscalationDetails")

}
