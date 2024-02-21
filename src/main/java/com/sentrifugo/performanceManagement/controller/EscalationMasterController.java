package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import com.sentrifugo.performanceManagement.repository.EscalationMasterRepository;
import com.sentrifugo.performanceManagement.service.EscalationMasterService;
import com.sentrifugo.performanceManagement.vo.EscalateListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/escalateMaster")
@CrossOrigin(origins = "*")

public class EscalationMasterController {

    @Autowired
    EscalationMasterService escalationMasterService;
    @Autowired
    EscalationMasterRepository repo;


    @PostMapping("/addEscalation/{employeeId}")
    public EscalationMaster addEscalationMaster(@RequestBody EscalationMaster escalationMaster, @PathVariable("employeeId") Integer employeeId) {
        return escalationMasterService.addEscalationMaster(escalationMaster, employeeId);
    }

    @GetMapping("/getEscalationDetails")
    public List<EscalationMaster> getEscalateMasterDetails() {
        return escalationMasterService.getEscalateMasterDetails();
    }

    @GetMapping("/getAllEscalationDetails")
    public List<EscalateListView> escalateListView() {
        return escalationMasterService.getAllEscalationDetails();
    }

    /* api created by Varsha Devgankar */
    @GetMapping("/getEsclationInDetailView/{id}")
    public ResponseEntity<?> findEscalationInDetailViewbyId(@PathVariable("id") Integer id) {
        Map<String, Object> escalationDetail = escalationMasterService.findEscalationInDetailViewbyId(id);
        if (!escalationDetail.isEmpty()) {
            return ResponseEntity.ok()
                    .body(escalationDetail);
        }
        return ResponseEntity.badRequest().body("No record");
    }

    @PostMapping("/addHrComment/{id}")
    public ResponseEntity<?> addingHrComments(@PathVariable("id") Integer id, @RequestBody String string) {
        String ans = string.trim();
        if (!ans.isEmpty()) {
            escalationMasterService.addHrComments(id, string);
            return ResponseEntity.ok().body("Hr Comments added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Hr Comments cannot be empty");
        }
    }


@PutMapping("/statusUpdate/{id}/{str}")
public ResponseEntity<?> updateStatus(@PathVariable Integer id,@PathVariable String str)
{
    String ans = str.trim();
    if(!ans.isEmpty() && id!=null)
    {
        escalationMasterService.statusUpdate(id,str);
        return ResponseEntity.ok().body("Status changed to Closed Successfully");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error in changing Status");
}
@GetMapping("/showHrComments/{id}")
public ResponseEntity<?> getHrComments (@PathVariable Integer id)
{
    if(id!=null) {
        EscalationMaster lst=escalationMasterService.getHrviews(id);
        return ResponseEntity.ok().body(lst);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error in obtaining Hr comments");
}

@GetMapping("/getEscalationFilters")
public ResponseEntity<?> escalationFilters ()
{
   return ResponseEntity.ok().body(escalationMasterService.getEscalationFilters());
}

@GetMapping("/filter")
    public ResponseEntity<?>   filter(
            @RequestParam(name="designation",required = false) String designation,
            @RequestParam(name="department",required = false) String department,
            @RequestParam(name="status",required = false)String status){
    List<EscalateListView> list=null;
  if(designation!=null){
       list=escalationMasterService.getAllEscalationDetailsByDesignation(designation);

  }
  else if(department!=null){
      System.out.println("HERE");
      list=escalationMasterService.getAllEscalationDetailsByDepartment(department);


  }
  else if(status!=null){
      list=escalationMasterService.getAllEscalationDetailsByStatus(status);
  }
    return ResponseEntity.ok().body(list);

}



}


