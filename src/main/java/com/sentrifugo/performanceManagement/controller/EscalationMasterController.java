package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import com.sentrifugo.performanceManagement.service.EscalationMasterService;
import com.sentrifugo.performanceManagement.vo.EscalateListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/getEscalationDetails")
    public List<EscalationMaster> getEscalateMasterDetails(){
        return escalationMasterService.getEscalateMasterDetails();
    }

    @GetMapping("/getAllEscalationDetails")
    public List<EscalateListView> escalateListView(){
        return escalationMasterService.getAllEscalationDetails();
    }

    /* api created by Varsha Devgankar */
    @GetMapping("/getEsclationInDetailView/{id}")
    public ResponseEntity<?> findEscalationInDetailViewbyId(@PathVariable("id") Integer id){
        Map<String,Object> escalationDetail = escalationMasterService.findEscalationInDetailViewbyId(id);
        if(!escalationDetail.isEmpty()){
          return   ResponseEntity.ok()
                    .body(escalationDetail);
        }
        return ResponseEntity.badRequest().body("No record");
    }

}
