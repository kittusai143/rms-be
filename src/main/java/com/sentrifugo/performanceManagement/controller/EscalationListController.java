package com.sentrifugo.performanceManagement.controller;
import com.sentrifugo.performanceManagement.entity.EscalationList;
import com.sentrifugo.performanceManagement.service.EscalationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/escalation")
public class EscalationListController {
    @Autowired
    private EscalationListService es;

    @GetMapping("/getByDesignation/{designation}")
    public ResponseEntity<?> getEmployeeByDesignation(@PathVariable String designation) {
        try {
            List<EscalationList> resultList = es.getEmployeeBYDesig(designation);
            if (!resultList.isEmpty()) {
                return ResponseEntity.ok().body(resultList);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("message", "No records found for the given designation");
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getByDepartment/{department}")
    public ResponseEntity<?> getEmployeeByDepartment(@PathVariable String department) {
        try {
            List<EscalationList> resultList = es.getEmployeeBYDepart(department);
            if (!resultList.isEmpty()) {
                return ResponseEntity.ok().body(resultList);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("message", "No records found for the given department");
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<?> getEmployeeByStatus(@PathVariable String status) {
        try {
            List<EscalationList> resultList = es.getEmployeeBYStatus(status);
            if (!resultList.isEmpty()) {
                return ResponseEntity.ok().body(resultList);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("message", "No records found for the given status");
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
