package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import com.sentrifugo.performanceManagement.repository.EscalationMasterRepository;
import com.sentrifugo.performanceManagement.service.EscalationMasterService;
import com.sentrifugo.performanceManagement.vo.EscalateListView;
import com.sentrifugo.performanceManagement.vo.EscalationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ResponseEntity<?>  addEscalationMaster(@RequestBody EscalationMaster escalationMaster, @PathVariable("employeeId") Integer employeeId) {
        try
        {
            EscalationMaster esc_master= escalationMasterService.addEscalationMaster(escalationMaster, employeeId);
            if(esc_master!=null)
            {
                return ResponseEntity.ok(esc_master);
            }
            else
            {
                Map<String,String> map= new HashMap<>();
                map.put("message","The data for provided employee/appraisal id does not exists");
                return ResponseEntity.ok(map);
            }
        }
        catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getEscalationDetails")
    public ResponseEntity<?> getEscalateMasterDetails() {

        try
        {
            List<EscalationMaster> list=escalationMasterService.getEscalateMasterDetails();
            if(!list.isEmpty())
            {
                return ResponseEntity.ok(list);
            }
            else
            {
                Map<String,String> map=new HashMap<>();
                List<Map<String,String>> l=new ArrayList<>();
                map.put("message","data not exist");
                l.add(map);
                return ResponseEntity.ok(l);
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getAllEscalationDetails")
    public ResponseEntity<?>escalateListView() {
        try
        {
            List<EscalateListView> list= escalationMasterService.getAllEscalationDetails();
            if(!list.isEmpty())
            {
                return ResponseEntity.ok(list);
            }
            else
            {
                Map<String,String> map=new HashMap<>();
                List<Map<String,String>> l=new ArrayList<>();
                map.put("message","data not exist");
                l.add(map);
                return ResponseEntity.ok(l);
            }
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }
    @GetMapping("/getEsclationInDetailView/{id}")
    public ResponseEntity<?> findEscalationInDetailViewbyId(@PathVariable("id") Integer id) {
        try {
            Map<String, Object> escalationDetail = escalationMasterService.findEscalationInDetailViewbyId(id);
            if (!escalationDetail.isEmpty()) {
                return ResponseEntity.ok().body(escalationDetail);
            }
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "No record");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/addHrComment/{id}")
    public ResponseEntity<?> addingHrComments(@PathVariable("id") Integer id, @RequestBody String string) {
        try {
            if (!string.isEmpty()) {
                escalationMasterService.addHrComments(id, string);
                return ResponseEntity.ok().body("Hr Comments added successfully");
            } else {
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("message", "Hr Comments cannot be empty");
                return ResponseEntity.ok().body(responseMap);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/statusUpdate/{id}/{str}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @PathVariable String str) {
        try {
            String ans = str.trim();
            if (!ans.isEmpty() && id != null) {
                escalationMasterService.statusUpdate(id, str);
                return ResponseEntity.ok().body("Status changed to Closed Successfully");
            }
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Error in changing Status");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/showHrComments/{id}")
    public ResponseEntity<?> getHrComments(@PathVariable Integer id) {
        try {
            if (id != null) {
                EscalationMaster lst = escalationMasterService.getHrviews(id);
                return ResponseEntity.ok().body(lst);
            }
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Error in obtaining Hr comments");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getEscalationFilters")
    public ResponseEntity<?> escalationFilters() {
        try {
            EscalationFilter esc=escalationMasterService.getEscalationFilters();
            if(esc!=null)
            {
                return ResponseEntity.ok().body(esc);
            }
            Map<String,String> map=new HashMap<>();
            map.put("message","data not exist");
            return ResponseEntity.ok().body(map);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(name = "designation", required = false) String designation,
            @RequestParam(name = "department", required = false) String department,
            @RequestParam(name = "status", required = false) String status) {
        try {
            List<EscalateListView> list = null;
            List <Map<String,String>> l=new ArrayList<>();
            Map<String,String> map=new HashMap<>();
            if (designation != null) {
                list = escalationMasterService.getAllEscalationDetailsByDesignation(designation);
            } else if (department != null) {
                list = escalationMasterService.getAllEscalationDetailsByDepartment(department);
            } else if (status != null) {
                list = escalationMasterService.getAllEscalationDetailsByStatus(status);
            }
            if (list == null || list.isEmpty()) {
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("message", "No records found");
                return ResponseEntity.ok().body(responseMap);
            }
            map.put("message","data not exist");
            l.add(map);
            return ResponseEntity.ok().body(l);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }



}