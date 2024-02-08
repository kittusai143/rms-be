package com.sentrifugo.performanceManagement.controller;
import com.sentrifugo.performanceManagement.entity.EscalationList;
import com.sentrifugo.performanceManagement.service.EscalationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/escalation")
public class EscalationListController {
    @Autowired
    private EscalationListService es;

    @GetMapping
    public ResponseEntity<List<EscalationList>> getAll()
    {
        return es.getAll();
    }
    @GetMapping("/getByDesignation/{designation}")

    public ResponseEntity<List<EscalationList>> getEmployeeByDesignation(@PathVariable String designation)
    {
        return es.getEmployeeBYDesig(designation);
    }

    @GetMapping("/getByDepartment/{department}")

    public ResponseEntity<List<EscalationList>> getEmployeeByDepartment(@PathVariable String department)
    {
        return es.getEmployeeBYDepart(department);
    }

    @GetMapping("/getByStatus/{status}")

    public ResponseEntity<List<EscalationList>> getEmployeeByStatus(@PathVariable String status)
    {
        return es.getEmployeeBYStatus(status);
    }

}
